package com.zhuxi.common.exception;

import lombok.Data;

public class JwtException extends LocationException{
    private int code;
    public JwtException(String message) {
        super(message);
        code = 401;
    }
}
