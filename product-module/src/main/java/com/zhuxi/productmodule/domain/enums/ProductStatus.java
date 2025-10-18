package com.zhuxi.productmodule.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuxi
 */
@Getter
@AllArgsConstructor
public enum ProductStatus {
    NO_PUBLISH(0,"未发布"),
    SALES(1,"在售"),
    RESERVE(2,"已预订"),
    SOLD(3,"已售出"),
    REVIEWING(4,"审核中");

    private final Integer code;
    private final String message;

    public static ProductStatus getByCode(Integer code) {
        for (ProductStatus value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("无效的商品状态码 " + code);
    }

}
