package com.zhuxi.user.module.domain.user.valueObject;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * @author zhuxi
 */
@Data
@NoArgsConstructor
public class Email {
    private String address;
    public Email(String address) {
        this.address = address;
    }
}
