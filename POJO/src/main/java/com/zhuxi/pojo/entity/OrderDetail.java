package com.zhuxi.pojo.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
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
}
