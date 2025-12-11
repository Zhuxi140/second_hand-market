package com.zhuxi.order.module.domain.objectValue;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * @author zhuxi
 */
@Getter
public class OrderDetail {

    private Long id;
    private Long orderId;
    private Long productId;
    private Long skuId;
    private Long sellerId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String productSnapshot;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private OrderDetail(Long id, Long orderId, Long productId, Long skuId, Long sellerId, Integer quantity, BigDecimal unitPrice, String productSnapshot,LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.skuId = skuId;
        this.sellerId = sellerId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productSnapshot = productSnapshot;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public OrderDetail() {
    }

    public static OrderDetail create(Long productId, Long skuId, Long sellerId, Integer quantity, BigDecimal unitPrice, String productSnapshot){
        OrderDetail detail = new OrderDetail();
        detail.productId = productId;
        detail.skuId = skuId;
        detail.sellerId = sellerId;
        detail.quantity = quantity;
        detail.productSnapshot = productSnapshot;
        detail.unitPrice = unitPrice;
        return detail;
    }
}