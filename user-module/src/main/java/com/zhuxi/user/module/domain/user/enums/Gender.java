package com.zhuxi.user.module.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author zhuxi
 */
@AllArgsConstructor
@Getter
public enum Gender {
    MALE(1),
    FEMALE(2),
    UNKNOWN(3);

    private final int code;

    public static Gender getByCode(int code) {
        for (Gender value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("无效的code:" + code);
    }

}
