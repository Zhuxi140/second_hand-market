package com.zhuxi.usermodule.domain.user.valueObject;

import lombok.Value;

@Value
public class Email {
    private String address;

    public Email(String address) {
        this.address = address;
    }
}
