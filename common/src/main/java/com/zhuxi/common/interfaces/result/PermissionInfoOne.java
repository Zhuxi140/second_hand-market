package com.zhuxi.common.interfaces.result;

import com.zhuxi.common.shared.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhuxi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionInfoOne {
    private Long userId;
    private Role role;
    private String permissionCode;
}
