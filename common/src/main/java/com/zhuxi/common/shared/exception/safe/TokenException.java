package com.zhuxi.common.shared.exception.safe;

import com.zhuxi.common.shared.exception.LocationException;

public class TokenException extends LocationException {
    private int code;
    public TokenException(String message) {
        super(message);
        code = 401;
    }
}
