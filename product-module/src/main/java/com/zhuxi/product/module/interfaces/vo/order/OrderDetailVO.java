package com.zhuxi.product.module.interfaces.vo.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDetailVO {
    private String orderSn;
    private Integer status;
    private String buyerSn;
    private String sellerSn;
    private String productSn;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
