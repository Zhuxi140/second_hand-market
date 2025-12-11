package com.zhuxi.order.module.domain.enums;

import lombok.Getter;

/**
 * @author zhuxi
 */

@Getter
public enum OrderStatus {
    PENDING_PAYMENT(0, "待支付"),
    PAID(1, "已支付"),
    SHIPPED(2, "已发货"),
    COMPLETED(3, "已完成"),
    CANCELED(4, "已取消"),
    REFUNDED(5, "已退款");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

}
