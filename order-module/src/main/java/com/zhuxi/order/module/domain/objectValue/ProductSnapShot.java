package com.zhuxi.order.module.domain.objectValue;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zhuxi
 */
@Getter
public class ProductSnapShot {

    private boolean isSh;
    private String productSn;
    private String skuSn;
    private String sellerSn;
    private String title;
    private String description;
    private String specifications;
    private String imageUrl;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;

    private ProductSnapShot(boolean isSh, String productSn, String skuSn, String sellerSn, String title, String description, String specifications, String imageUrl, BigDecimal price, Integer quantity, BigDecimal totalPrice, LocalDateTime createdAt) {
        this.isSh = isSh;
        this.productSn = productSn;
        this.skuSn = skuSn;
        this.sellerSn = sellerSn;
        this.title = title;
        this.description = description;
        this.specifications = specifications;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public static ProductSnapShot create(boolean isSh, String productSn, String skuSn, String sellerSn,
                                         String title, String description, String specifications,
                                         String imageUrl, BigDecimal price, Integer quantity, BigDecimal totalPrice) {
        return new ProductSnapShot(isSh, productSn, skuSn, sellerSn, title, description,
                specifications, imageUrl, price, quantity, totalPrice, LocalDateTime.now());
    }
}
