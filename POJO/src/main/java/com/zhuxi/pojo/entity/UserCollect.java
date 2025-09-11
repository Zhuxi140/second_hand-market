package com.zhuxi.pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserCollect {
    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
