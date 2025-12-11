package com.zhuxi.order.module.domain.objectValue;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author zhuxi
 */

@Getter
public class ProductInfo {

    private Long productId;
    private BigDecimal price;
    private String description;
    private String title;


    private ProductInfo(Long productId, BigDecimal price, String description, String title) {
        this.productId = productId;
        this.price = price;
        this.description = description;
        this.title = title;
    }

    public ProductInfo() {
    }
}
