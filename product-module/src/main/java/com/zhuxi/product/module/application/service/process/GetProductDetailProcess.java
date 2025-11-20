package com.zhuxi.product.module.application.service.process;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
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
import org.springframework.stereotype.Component;

import java.util.List;

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

    public ProductDetailVO handleProductNotInCache(String productSn, List<ProductStatic> cachedStatics) {
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

    public ProductDetailVO handlerProductInCache(Product product, List<ProductStatic> productStatics){
        //获取卖家信息
        List<Object> sellerInfo = null;
        String userSn = cache.getUserSn(product.getSellerId());
        if (userSn != null){
            sellerInfo = cache.getSellerInfo(userSn);
            if (sellerInfo == null){
                sellerInfo = repository.getSellerInfo(userSn);
                if (sellerInfo == null){
                    commonCache.saveNullValue(properties.getUserInfoKey() + userSn);
                    return null;
                }
            }
        }

        // 缓存全部命中 直接组装返回vo
        if (productStatics != null && sellerInfo != null){
            return ToProductDetailVO.COVERT.toProductDetailVO(product, sellerInfo, productStatics, userSn);
        }

        if (productStatics == null){
            List<ProductStatic> productStatics1 = repository.getProductStatics(product.getId());
            if (productStatics1 == null){
                commonCache.saveNullValue(properties.getProductStaticKey() + product.getProductSn().getSn());
                return null;
            }
            return ToProductDetailVO.COVERT.toProductDetailVO(product, sellerInfo, productStatics1, userSn);
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
