package com.zhuxi.pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductSort {
    private Long id;
    private String name;
    private Long parentId;
    private String iconUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
