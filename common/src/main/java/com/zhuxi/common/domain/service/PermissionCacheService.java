package com.zhuxi.common.domain.service;

import com.zhuxi.common.interfaces.result.PermissionInfo;

/**
 * @author zhuxi
 */

public interface PermissionCacheService {

    PermissionInfo getPermissionInfo(String userSn);
}
