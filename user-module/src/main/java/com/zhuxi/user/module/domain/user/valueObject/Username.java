package com.zhuxi.user.module.domain.user.valueObject;

import lombok.Value;

@Value
public class Username {

    private String accountName;

    public Username(String accountName) {

        this.accountName = accountName;
    }

}
