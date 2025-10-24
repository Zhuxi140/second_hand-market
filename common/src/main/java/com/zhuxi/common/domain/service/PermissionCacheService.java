package com.zhuxi.common.domain.service;

import com.zhuxi.common.interfaces.result.PermissionInfo;
import com.zhuxi.common.shared.enums.Role;

/**
 * @author zhuxi
 */

public interface PermissionCacheService {

    PermissionInfo getPermissionInfo(String userSn);

    boolean checkBlackToken(String token, Role role);
}
