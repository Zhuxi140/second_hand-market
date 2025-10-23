package com.zhuxi.common.shared.enums;

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

    private final int Id;

    public static Role getRoleById(int id) {
        for (Role role : Role.values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("错误的角色码, Id:"+ id);
    }
}
