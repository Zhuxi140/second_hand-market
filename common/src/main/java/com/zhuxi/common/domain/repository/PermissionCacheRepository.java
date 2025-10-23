package com.zhuxi.common.domain.repository;

import com.zhuxi.common.interfaces.result.PermissionInfo;
import com.zhuxi.common.interfaces.result.PermissionInfoOne;

import java.util.List;

/**
 * @author zhuxi
 */
public interface PermissionCacheRepository {

    Long getUserId(String userSn);

    List<String> getUserPermissionInfo(Long userId);

    List<PermissionInfoOne> getRolePermissionInfo(Long userId);
}
