package com.zhuxi.user.module.infrastructure.mapper;

import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.valueObject.RefreshToken;
import com.zhuxi.user.module.interfaces.vo.user.UserViewVO;
import org.apache.ibatis.annotations.*;

/**
 * @author zhuxi
 */
@Mapper
public interface UserMapper {

    // 通用save
    @Insert("""
            INSERT INTO user (userSn, username, password, nickname, phone, avatar,userStatus,gender)
            VALUES (#{userSn}, #{username}, #{password}, #{nickname}, #{phone},#{avatar}, #{userStatus},#{gender})
    """)
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int save(User user);

    // 通用 update
    int update(User user);

    //添加角色映射
    @Select("INSERT INTO user_role(user_id, role_id) VALUE (#{userId},#{role})")
    int saveRole(Long userId, int roleId);

    //检查角色是否存在
    @Select("SELECT 1 FROM role WHERE id = #{roleId}")
    Integer checkRoleExist(int roleId);

    // 根据userId获取用户角色
    @Select("SELECT ur.role_id FROM user_role ur JOIN user u ON ur.user_id = u.id WHERE u.id = #{userId}")
    Role getRole(Long userId);

    // 判断用户名是否存在
    @Select("SELECT 1 FROM user WHERE username = #{username}")
    Integer checkUsernameExist(String username);

    @Select("""
    SELECT
        id,
        userSn,
        password,
        nickname,
        userStatus,
        ur.role_id AS role,
        avatar
    FROM user u JOIN user_role ur ON u.id = ur.user_id
    WHERE username = #{username}
    """)
    User getUserByUsername(String username);

    //获取用户信息
    @Select("""
    SELECT
        userSn,
        username,
        nickname,
        gender,
        phone,
        email,
        avatar,
        ur.role_id AS role,
        userStatus,
        u.created_at
    FROM user u JOIN user_role ur ON u.id = ur.user_id
    WHERE userSn = #{userSn}
    """)
    UserViewVO getUserInfo(String userSn);

    @Select("""
    SELECT
        id,
        userStatus,
        ur.role_id AS role
    FROM user u JOIN user_role ur ON u.id = ur.user_id
    WHERE userSn = #{userSn}
    """)
    User getISBySn(String userSn);

    @Select("SELECT id FROM user WHERE userSn = #{userSn}")
    Long getUserId(String userSn);

    // 修改密码
    @Update("UPDATE user SET password = #{newPw} WHERE id = #{id}")
    int updatePw(Long id, String newPw);

    //写入 刷新令牌
    @Insert("""
    INSERT INTO
    refresh_token(user_id, token_hash, expires_at, ip_address, user_agent)
    VALUE (#{userId}, #{tokenHash}, #{expiresAt}, #{ipAddress}, #{userAgent})
    """)
    int saveToken(RefreshToken token);

    @Select("""
    SELECT
    rt.token_hash,rt.expires_at
    FROM refresh_token rt JOIN user u ON rt.user_id = u.id
    WHERE is_delete != 1 AND rt.user_id = #{userId}
    """)
    RefreshToken getTokenByUserId(Long userId);

    @Select("SELECT id, user_id, token_hash, expires_at, ip_address, user_agent FROM refresh_token WHERE is_delete != 1 and user_id = #{userId} and now() <= expires_at")
    RefreshToken checkFreshTokenExist(Long userId);


}
