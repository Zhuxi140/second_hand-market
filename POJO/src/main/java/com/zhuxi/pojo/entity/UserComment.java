package com.zhuxi.pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserComment {
    private Long id;
    private Long orderId;
    private Long reviewerId;
    private Long revieweeId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
