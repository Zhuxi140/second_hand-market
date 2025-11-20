package com.zhuxi.product.module.application.service.process;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.BusinessException;
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
import java.util.List;
import java.util.Map;

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
            asyncFillAllCaches(vo, productSn,needStatic);
            return vo;

        } catch (BusinessException e) {
            commonCache.saveNullValue(properties.getShProductKey() + productSn);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 获取商品详细信息(命中(部分)缓存)
     * @param product 商品信息
     * @param productStatics 商品静态信息
     * @return 获取商品详细信息
     */
    public ProductDetailVO handlerProductInCache(Product product, List<ProductStatic> productStatics){
        //获取卖家信息
        Long sellerId = product.getSellerId();
        String sellerSn = cache.getUserSn(sellerId);
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
            asyncProductStatic(product, productStatics1);
            return ToProductDetailVO.COVERT.toProductDetailVO(product, sellerInfo, productStatics1, sellerSn);
        }
    }

    @Async
    protected void asyncFillAllCaches(ProductDetailVO vo, String productSn,boolean needStatic){
        String shProductKey = properties.getShProductKey() + productSn;
        String productStaticKey = properties.getProductStaticKey() + productSn;
        String userInfoKey = properties.getUserInfoKey() + vo.getSellerSn();
        try {
            // 缓存未命中product主体信息 缺失过多 直接删除整个key 重新缓存
            commonCache.delKey(shProductKey);
            Product product = repository.getProductForCache(vo.getId());
            product.addNames(vo.getCategoryName(), vo.getConditionName());
            cache.saveShProduct(product, productSn);


        if (needStatic) {
            commonCache.delKey(productStaticKey);
            List<ProductStatic> productStatics = repository.getProductStatics(vo.getId());
            cache.saveProductStatic(productStatics, productSn);
        }

            commonCache.hashFlushValue(
                    Map.of("nickName",vo.getSellerName(), "avatar", vo.getSellerAvatar()),
                    userInfoKey
            );
        } catch (Exception e) {
            log.error("异步回填缓存出错。productSn:{},needStatic:{},error_Info:{}",productSn,needStatic,e.getMessage());
            // 其他预警或重试处理
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
    protected void asyncProductStatic(Product product, List<ProductStatic> productStatics){
            cache.saveProductStatic(productStatics, product.getProductSn().getSn());
    }
}
