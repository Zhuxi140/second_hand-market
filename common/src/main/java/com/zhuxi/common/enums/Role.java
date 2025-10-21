package com.zhuxi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuxi
 */

@AllArgsConstructor
@Getter
public enum Role {
    admin(4),
    super_admin(3),
    user(1),
    Merchant(2);

    private final int Level;
}
