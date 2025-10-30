package com.zhuxi.product.module.domain.model;

import com.zhuxi.product.module.domain.enums.ImageType;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author zhuxi
 */
@Getter
@NoArgsConstructor
public class ProductStatic {
    private Long id;
    private Long productId;
    private Long skuId;
    private String imageUrl;
    private ImageType imageType;
    private Integer sortOrder;

    private ProductStatic(Long id, Long productId, Long skuId, String imageUrl, ImageType imageType, Integer sortOrder) {
        this.id = id;
        this.productId = productId;
        this.skuId = skuId;
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.sortOrder = sortOrder;
    }
}
