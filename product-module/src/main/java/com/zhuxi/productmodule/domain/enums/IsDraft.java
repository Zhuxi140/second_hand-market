package com.zhuxi.productmodule.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuxi
 */
@AllArgsConstructor
@Getter
public enum IsDraft {
    ENABLE(1, "草稿"),
    DISABLE(0, "发布");

    private final Integer code;
    private final String message;

    public static IsDraft getByCode(Integer code) {
        for (IsDraft value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("无效的草稿状态码: " + code);
    }
}
