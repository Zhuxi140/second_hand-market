package com.zhuxi.common.shared.exception;

/**
 * @author zhuxi
 */
public class PermissionException extends LocationException{

    private final int code;
    public PermissionException(String message, int code) {
        super(message);
        this.code = code;
    }
    public PermissionException(String message) {
        super(message);
        this.code = 4001;
    }
}
