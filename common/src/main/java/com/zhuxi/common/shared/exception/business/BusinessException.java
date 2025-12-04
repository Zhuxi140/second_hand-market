package com.zhuxi.common.shared.exception.business;

import com.zhuxi.common.shared.exception.LocationException;
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
