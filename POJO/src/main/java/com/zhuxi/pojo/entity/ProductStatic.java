package com.zhuxi.pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductStatic {
    private Long id;
    private Long productId;
    private Long skuId;
    private String imageUrl;
    private Integer imageType;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
