package com.zhuxi.common.exception;

/**
 * @author zhuxi
 */
public class COSException extends LocationException {
    private final int code;
    public COSException(String message, int code) {
        super(message);
        this.code = code;
    }
    public COSException(String message) {
        super(message);
        this.code = 4001;
    }
}
