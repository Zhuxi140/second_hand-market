package com.zhuxi.pojo.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductSpec {
    private Long id;
    private String skuSn;
    private Long productId;
    private String specifications;
    private BigDecimal price;
    private Integer stock;
    private String skuCode;
    private LocalDateTime updatedAt;
}
