package com.zhuxi.product.module.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuxi
 */
@Getter
public enum IsDraft {
    ENABLE(1, "草稿"),
    DISABLE(0, "发布");

    private final Integer code;
    private final String message;

    @JsonCreator
    IsDraft(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public static IsDraft getByCode(Integer code) {
        for (IsDraft value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("无效的草稿状态码: " + code);
    }
}
