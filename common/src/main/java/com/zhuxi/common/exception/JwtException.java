package com.zhuxi.common.exception;

public class JwtException extends LocationException{
    private int code;
    public JwtException(String message) {
        super(message);
        code = 401;
    }
}
