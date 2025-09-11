package com.zhuxi.pojo.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserOrder {
    private Long id;
    private String orderSn;
    private Long buyerId;
    private BigDecimal totalAmount;
    private Integer status;
    private Long receiveAddressId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
