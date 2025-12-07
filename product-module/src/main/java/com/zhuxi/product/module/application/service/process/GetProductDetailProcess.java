package com.zhuxi.product.module.application.service.process;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.product.module.application.assembler.ToProductDetailVO;
import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.model.ProductStatic;
import com.zhuxi.product.module.domain.repository.ProductRepository;
import com.zhuxi.product.module.domain.service.ProductCacheService;
import com.zhuxi.product.module.interfaces.vo.ProductDetailVO;
import com.zhuxi.product.module.interfaces.vo.ProductImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuxi
 */
@Component
@Slf4j(topic = "GetProductDetailProcess")
@RequiredArgsConstructor
public class GetProductDetailProcess {

    private final ProductRepository repository;
    private final CommonCacheService commonCache;
    private final CacheKeyProperties properties;
    private final ProductCacheService cache;


    /**
     * 获取商品详细信息(未命中缓存)
     * @param productSn 商品编号
     * @return 商品详细信息
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public ProductDetailVO handleProductNotInCache(String productSn, List<ProductStatic> cachedStatics) {
        try {
            // 判断是否需要查询静态信息
            boolean needStatic = (cachedStatics == null);
            ProductDetailVO vo = repository.getShProductDetail(productSn, needStatic);

            if (vo == null) {
                // 数据库也不存在，缓存空值防止穿透
                commonCache.saveNullValue(properties.getShProductKey() + productSn);
                log.error("获取商品详细信息出错-查库获取数据为null,key:{}", properties.getShProductKey() + productSn);
                throw new BusinessException(BusinessMessage.DATA_EXCEPTION);
            }

            if (needStatic){
                if (vo.getProductImages() == null){
                    String shProductImageKey = properties.getProductStaticKey() + productSn;
                    commonCache.saveNullValue(shProductImageKey);
                    log.error("获取商品静态信息出错-查库获取数据为null,key:{}", shProductImageKey);
                    throw new BusinessException(BusinessMessage.DATA_EXCEPTION);
                }
            }

            // 判断是否为热门数据
            boolean isHostData = checkHotData(vo.getHostScore());

            // 静态数据缓存命中 直接使用缓存数据
            if (!needStatic) {
                List<ProductImage> imageList = cachedStatics.stream()
                        .map(s -> {
                            ProductImage image = new ProductImage();
                            image.setSkuId(s.getSkuId());
                            image.setImageUrl(s.getImageUrl());
                            image.setType(s.getImageType().getCode());
                            return image;
                        }).toList();
                vo.setProductImages(imageList);
            }

            // 异步回填缓存
            asyncFillAllCaches(vo, productSn,needStatic,isHostData);
            return vo;

        } catch (BusinessException e) {
            commonCache.saveNullValue(properties.getShProductKey() + productSn);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 获取商品详细信息(命中(或部分)缓存)
     * @param product 商品信息
     * @param productStatics 商品静态信息
     * @return 获取商品详细信息
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public ProductDetailVO handlerProductInCache(Product product, List<ProductStatic> productStatics){

        // 判断是否为热门数据
        boolean isHostData = checkHotData(product.getHostScore().getScore());
        //获取卖家信息
        Long sellerId = product.getSellerId();
        String sellerSn = commonCache.getUserSn(sellerId);
        if (sellerSn == null) {
            sellerSn = repository.getUserSn(sellerId);
            if (sellerSn == null){
                log.error("获取卖家信息出错-查库获取数据为null,method:getUserSn(sellerId:{})", sellerId);
                throw new BusinessException(BusinessMessage.DATA_EXCEPTION);
            }
        }

        List<Object> sellerInfo = cache.getSellerInfo(sellerSn);
        if (sellerInfo == null){
            sellerInfo = repository.getSellerInfo(sellerSn);
            if (sellerInfo == null){
                String userInfoKey = properties.getUserInfoKey() + sellerSn;
                commonCache.saveNullValue(userInfoKey);
                log.error("获取卖家信息出错-查库获取数据为null,key:{},method:getSellerInfo(sellerSn:{})", userInfoKey, sellerSn);
                throw new BusinessException(BusinessMessage.DATA_EXCEPTION);
            }
            // 异步回填卖家信息缓存
            asyncSellerInfo(sellerInfo,sellerSn);
        }

        // productStatics缓存命中 直接组装返回vo
        if (productStatics != null){
            return ToProductDetailVO.COVERT.toProductDetailVO(product, sellerInfo, productStatics, sellerSn);
        }

        // productStatics未命中
        List<ProductStatic> productStatics1 = repository.getProductStatics(product.getId());
        if (productStatics1 == null){
            commonCache.saveNullValue(properties.getProductStaticKey() + product.getProductSn().getSn());
            log.error("获取商品静态信息出错-查库获取数据为null,key:{}", properties.getProductStaticKey() + product.getProductSn().getSn());
            throw new BusinessException(BusinessMessage.DATA_EXCEPTION);
        }else{
            asyncProductStatic(product, productStatics1,isHostData);
            return ToProductDetailVO.COVERT.toProductDetailVO(product, sellerInfo, productStatics1, sellerSn);
        }
    }

    @Async
    protected void asyncFillAllCaches(ProductDetailVO vo, String productSn,
                                      boolean needStatic,boolean isHostData){
        String shProductKey = properties.getShProductKey() + productSn;
        String productStaticKey = properties.getProductStaticKey() + productSn;
        String userInfoKey = properties.getUserInfoKey() + vo.getSellerSn();
        long threadId = Thread.currentThread().getId();
        try {
            // 缓存未命中product主体信息 缺失过多 直接删除整个key 重新缓存
            Boolean lock = commonCache.getLock(properties.getShProductLockKey() + productSn, String.valueOf(threadId), 30, TimeUnit.SECONDS);
            if (!lock){
                log.warn("getLock-failed,已经有线程在处理缓存回填,productSn:{}",productSn);
                return;
            }
            commonCache.delKey(shProductKey);
            Product product = repository.getProductForCache(vo.getId());
            product.addNames(vo.getCategoryName(), vo.getConditionName());
            cache.saveShProduct(product, productSn,isHostData);

            if (needStatic) {
                commonCache.delKey(productStaticKey);
                List<ProductStatic> productStatics = repository.getProductStatics(vo.getId());
                cache.saveProductStatic(productStatics, productSn,isHostData);
            }

            commonCache.hashFlushValue(
                    Map.of("nickName",vo.getSellerName(), "avatar", vo.getSellerAvatar()),
                    userInfoKey
            );
        } catch (Exception e) {
            log.error("异步回填缓存出错。productSn:{},needStatic:{},error_Info:{}",productSn,needStatic,e.getMessage());
            // 其他预警或重试处理
        }finally {
            try {
                commonCache.unLock(properties.getShProductLockKey() + productSn, String.valueOf(threadId));
            }catch (Exception e){
                log.warn("释放锁异常-message:{}", e.getMessage());
            }
        }
    }

    @Async
    protected void asyncSellerInfo(List<Object> sellerInfo,String sellerSn){
            String sellerInfoKey = properties.getUserInfoKey() + sellerSn;
            commonCache.hashFlushValue(
                    Map.of("nickName",sellerInfo.get(0), "avatar", sellerInfo.get(1)),
                    sellerInfoKey
            );

    }

    @Async
    protected void asyncProductStatic(Product product, List<ProductStatic> productStatics, boolean isHostData){
            cache.saveProductStatic(productStatics, product.getProductSn().getSn(), isHostData);
    }

    private boolean checkHotData(BigDecimal hostScore){
        if (hostScore == null){
            return false;
        }
        return hostScore.compareTo(BigDecimal.valueOf(80)) >= 0;
    }
}
