package com.zhuxi.common.exception;

public class TransactionalException extends LocationException {
    private int code;
    public TransactionalException(String message, int code) {
        super(message);
        this.code = code;
    }
    public  TransactionalException(String message) {
        super(message);
        this.code = 4001;
    }

    public int getCode() {
        return code;
    }
}
