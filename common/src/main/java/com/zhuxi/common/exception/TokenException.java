package com.zhuxi.common.exception;

public class TokenException extends LocationException{
    private int code;
    public TokenException(String message) {
        super(message);
        code = 401;
    }
}
