package com.zhuxi.user.module.domain.user.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author zhuxi
 */

@Getter
public enum UserStatus {
    ACTIVE(1,"正常"),
    LOCKED(0,"锁定");

    private final Integer code;
    private final String message;

    UserStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserStatus getByCode(Integer code) {
        for (UserStatus userStatus : values()) {
            if (Objects.equals(userStatus.code, code)) {
                return userStatus;
            }
        }
        throw new IllegalArgumentException("无效的用户状态码: " + code);
    }
}
