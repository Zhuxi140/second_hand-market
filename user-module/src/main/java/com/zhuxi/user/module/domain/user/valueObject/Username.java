package com.zhuxi.user.module.domain.user.valueObject;

import lombok.Value;

/**
 * @author zhuxi
 */
@Value
public class Username {

    String accountName;

    public Username(String accountName) {

        this.accountName = accountName;
    }

}
