package com.zhuxi.pojo.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private String productSn;
    private Long sellerId;
    private Long shopId;
    private String title;
    private String description;
    private Long categoryId;
    private BigDecimal price;
    private Integer condition;
    private Integer status;
    private String location;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
