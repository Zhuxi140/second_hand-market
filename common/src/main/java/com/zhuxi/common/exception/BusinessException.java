package com.zhuxi.common.exception;

import lombok.Getter;

/**
 * @author zhuxi
 */
@Getter
public class BusinessException extends LocationException {
    private final int code;
    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }
    public BusinessException(String message) {
        super(message);
        this.code = 4001;
    }

}
