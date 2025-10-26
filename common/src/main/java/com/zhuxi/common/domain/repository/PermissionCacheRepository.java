package com.zhuxi.common.domain.repository;

import com.zhuxi.common.interfaces.result.PermissionInfo;
import com.zhuxi.common.interfaces.result.PermissionInfoOne;

import java.util.List;

/**
 * @author zhuxi
 */
public interface PermissionCacheRepository {

    /**
     * 获取用户ID
     *
     * @param userSn 用户编号
     * @return 用户ID
     */
    Long getUserId(String userSn);

    /**
     * 获取用户被禁用的权限信息
     *
     * @param userId 用户ID
     * @return 用户权限信息
     */
    List<String> getBanedPermissionInfo(Long userId);

    /**
     * 获取角色拥有的权限信息
     *
     * @param userId 用户ID
     * @return 用户权限信息
     */
    List<PermissionInfoOne> getRolePermissionInfo(Long userId);
}
