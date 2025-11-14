package com.zhuxi.product.module.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuxi
 */
@Getter
public enum ProductStatus {
    NO_PUBLISH(0,"未发布"),
    SALES(1,"在售"),
    RESERVE(2,"已预订"),
    SOLD(3,"已售出"),
    REVIEWING(4,"审核中");

    private final Integer code;
    private final String message;

    @JsonCreator
    ProductStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ProductStatus getByCode(Integer code) {
        for (ProductStatus value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("无效的商品状态码 " + code);
    }

}
