package com.zhuxi.product.module.application.service;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.common.shared.exception.PersistenceException;
import com.zhuxi.product.module.application.assembler.ToProductDetailVO;
import com.zhuxi.product.module.application.service.process.PublishShProcess;
import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.model.ProductStatic;
import com.zhuxi.product.module.domain.repository.ProductRepository;
import com.zhuxi.product.module.domain.service.ProductCacheService;
import com.zhuxi.product.module.domain.service.ProductService;
import com.zhuxi.product.module.interfaces.dto.PublishSHDTO;
import com.zhuxi.product.module.interfaces.dto.UpdateProductDTO;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhuxi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductCacheService cache;
    private final CacheKeyProperties properties;
    private final PublishShProcess publishShProcess;
    private final CommonCacheService commonCache;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CategoryTreeVO> getCategoryList(int limit, int offset) {

        List<CategoryVO> list = cache.getCategoryList();
        if (list == null) {
            list = repository.getCategoryList(limit, offset);
            // 异步缓存
            List<CategoryVO> finalList = list;
            CompletableFuture.runAsync(() -> cache.saveCategoryList(finalList));
        }

        // 构建树形结构
        List<CategoryTreeVO> treeVO = list.stream()
                .map(CategoryTreeVO::new)
                .toList();

        return buildTree(treeVO);
    }

    @Override
    public String publishSh(PublishSHDTO sh, String userSn) {
        // 获取业务所需冗余字段
        List<String> otherNames = publishShProcess.getOtherNames(sh.getCategoryId(), sh.getCategoryId());
        // 发布商品
        Product product = publishShProcess.publish(sh, otherNames);

        try{
            //写入
            publishShProcess.save(product);
        }catch (PersistenceException e){
            // 失败，保存为草稿
            publishShProcess.saveDraft( product);
            throw new BusinessException(BusinessMessage.SAVE_PRODUCT_ERROR_SAVE_DRAFT);
        }
        // 异步缓存
        CompletableFuture.runAsync(()->{
            // 缓存商品信息
            cache.saveShProduct(product,product.getProductSn().getSn());
            // 缓存商品静态信息
            List<ProductStatic> productStatics = publishShProcess.getProductStatics(product.getId());
            cache.saveProductStatic(productStatics,product.getProductSn().getSn());
        });
        return product.getProductSn().getSn();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ConditionSHVO> getShConditions() {
        List<ConditionSHVO> shConditions = cache.getShConditions();
        if (shConditions == null){
            // 未命中
            shConditions = repository.getShConditions();
            List<ConditionSHVO> finalShConditions = shConditions;
            //异步缓存
            CompletableFuture.runAsync(() -> cache.saveConditionList(finalShConditions));
        }
        return shConditions;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ShProductVO> getShProductList(ShProductParam shProductParam) {

        boolean isFirst = shProductParam.getLastSn() == null;

        List<ShProductVO> list;
        if ("DESC".equals(shProductParam.getSortOrder())){
            setProductIdAndTime(shProductParam,isFirst,true);
            list = repository.getShProductListDesc(shProductParam);
        }else if ("ASC".equals(shProductParam.getSortOrder())){
            setProductIdAndTime(shProductParam,isFirst,false);
            list = repository.getShProductListAsc(shProductParam);
        }else {
            setProductIdAndTime(shProductParam,isFirst,true);
            list = repository.getShProductListDesc(shProductParam);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductDetailVO getShProductDetail(String productSn) {
        Product product = cache.getDetailProductInfo(productSn);
        List<ProductStatic> productStatics = cache.getProductStatics(productSn);
        if (product == null){
            // 商品详细主体信息未命中 直接查库
            ProductDetailVO productDetailVO = handleProductNotInCache(productSn, productStatics);
            if (productDetailVO == null){
                throw new BusinessException(BusinessMessage.GET_PRODUCT_DETAIL_ERROR);
            }
            return productDetailVO;
        }

        // 待完善
        ProductDetailVO productDetailVO = handlerProductInCache(product, productStatics);
        return productDetailVO;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateProduct(UpdateProductDTO update, String userSn) {
        // 获取商品id
        Long productId = repository.getProductIdBySn(update.getProductSn());
        // 获取用户id
        Long sellerId = repository.getUserIdBySn(userSn);
        update.setSellerId(sellerId);

        Product product = new Product();
        // 修改商品
        product.modify(update,productId);

        //写入
        repository.save(product);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void delProduct(String productSn) {

        Long productId = repository.getProductIdBySn(productSn);
        repository.delProduct(productId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MeShProductVO> getMeShProductList(String userSn) {
        return repository.getMeShProductList(repository.getUserIdBySn(userSn));
    }


    private List<CategoryTreeVO> buildTree(List<CategoryTreeVO> list){
        List<CategoryTreeVO> parentNodes = new ArrayList<>();

        for (CategoryTreeVO node : list){
            if (node.getParentId() == null || node.getParentId() == 0){
                parentNodes.add(node);
            }
        }

        for (CategoryTreeVO parentNode : parentNodes){
            findChildren(parentNode, list);
        }
        return parentNodes;
    }

    private void findChildren(CategoryTreeVO parentNode, List<CategoryTreeVO> allNodes){
        for (CategoryTreeVO node : allNodes){
            if (parentNode.getId().equals(node.getParentId())){
                if (parentNode.getChildren() == null){
                    parentNode.setChildren(new ArrayList<>());
                }
                parentNode.getChildren().add(node);
                findChildren(node, allNodes);
            }
        }
    }

    private void setProductIdAndTime(ShProductParam shProductParam,boolean isFirst,boolean isDesc){
        if (!isFirst){
            Long productId = repository.getProductIdBySn(shProductParam.getLastSn());
            shProductParam.setProductId(productId);
        }else{
            if (isDesc){
                shProductParam.setProductId(Long.MAX_VALUE);
                shProductParam.setLastCreatedAt(LocalDateTime.now());
            }else{
                shProductParam.setProductId(Long.MIN_VALUE);
                shProductParam.setLastCreatedAt(LocalDateTime.MIN);
            }
        }
    }

    private ProductDetailVO handleProductNotInCache(String productSn, List<ProductStatic> cachedStatics) {
        try {
            // 判断是否需要查询静态信息
            boolean needStatic = (cachedStatics == null);
            ProductDetailVO vo = repository.getShProductDetail(productSn, needStatic);

            if (vo == null) {
                // 数据库也不存在，缓存空值防止穿透
                commonCache.saveNullValue(properties.getShProductKey() + productSn);
                return null;
            }
            if (needStatic){
                if (vo.getProductImages() == null){
                    String shProductImageKey = properties.getProductStaticKey() + productSn;
                    commonCache.saveNullValue(shProductImageKey);
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

            // 异步回填所有缓存
            /*asyncFillAllCaches(vo, productSn);*/
            return vo;

        } catch (BusinessException e) {
            commonCache.saveNullValue(properties.getShProductKey() + productSn);
            throw new BusinessException(e.getMessage());
        }
    }

    private ProductDetailVO handlerProductInCache(Product  product,List<ProductStatic> productStatics){
        //获取卖家信息
        List<Object> sellerInfo = null;
        String userSn = cache.getUserSn(product.getSellerId());
        if (userSn != null){
            sellerInfo = cache.getSellerInfo(userSn);
        }

        // 缓存全部命中 直接组装返回vo
        if (productStatics != null && sellerInfo != null){
            return ToProductDetailVO.COVERT.toProductDetailVO(product, sellerInfo, productStatics, userSn);
        }

        //异步缓存 缺失信息
/*        CompletableFuture.runAsync(() -> {
            // 缓存商品信息
            cache.saveShProduct(vo.getProduct(),productSn);
            // 缓存商品静态信息
            cache.saveProductStatic(vo.getProductStatics(),productSn);
            // 缓存卖家信息
            cache.saveInfo(userSn,vo.getSellerInfo());
        });*/
        return null;
    }
}
