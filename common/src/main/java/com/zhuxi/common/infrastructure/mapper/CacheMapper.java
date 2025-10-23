package com.zhuxi.common.infrastructure.mapper;

import com.zhuxi.common.interfaces.result.PermissionInfo;
import com.zhuxi.common.interfaces.result.PermissionInfoOne;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zhuxi
 */
@Mapper
public interface CacheMapper {

    @Select("""
    SELECT
    p.code AS permissionCode
    FROM permission p JOIN user_permission up ON p.id = up.permission_id
    WHERE up.user_id = #{userId}
    """)
    List<String> getUserPermissionInfo(Long userId);


    @Select("""
    SELECT
    ur.user_id,
    rp.role_id AS role,
    p.code AS permissionCode
    FROM permission p JOIN role_permission rp ON p.id = rp.permission_id
        JOIN user_role ur ON rp.role_id = ur.role_id
    WHERE ur.user_id = #{userId}
    """)
    List<PermissionInfoOne> getRolePermissionInfo(Long userId);

    @Select("SELECT id FROM user WHERE userSn = #{userSn}")
    Long getUserId(String userSn);
}
