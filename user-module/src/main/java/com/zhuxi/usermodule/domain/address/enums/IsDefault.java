package com.zhuxi.usermodule.domain.address.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuxi
 */

@Getter
@AllArgsConstructor
public enum IsDefault {
    ENABLE(1,"默认地址"),
    DISABLE(0,"非默认地址");

    private final Integer code;
    private final String message;

    public static IsDefault getByCode(Integer code){
        for (IsDefault value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }

        throw new IllegalArgumentException("无效的默认地址状态码: " + code);
    }

}
