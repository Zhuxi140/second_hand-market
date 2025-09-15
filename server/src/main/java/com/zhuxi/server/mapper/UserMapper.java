package com.zhuxi.server.mapper;

import com.zhuxi.pojo.DTO.User.UserRegisterDTO;
import com.zhuxi.pojo.VO.User.UserViewVO;
import com.zhuxi.pojo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    // 插入用户
    @Insert("""
            INSERT INTO user (userSn, username, password, nickname, phone, status, role)
            VALUES (#{userSn}, #{username}, #{password}, #{nickname}, #{phone}, 1, #{role})
    """)
    int register(UserRegisterDTO user);

    // 判断用户名是否存在
    @Select("SELECT 1 FROM user WHERE username = #{username}")
    Integer checkUsernameExist(String username);

    @Select("""
    SELECT
        userSn,
        username,
        password,
        nickname,
        role,
        avatar
    FROM user WHERE username = #{username}
    """)
    User getPasswordByUsername(String username);

    //获取用户信息
    @Select("""
    SELECT
        userSn,
        username,
        nickname,
        phone,
        email,
        avatar,
        role,
        status,
        created_at
    FROM user WHERE userSn = #{userSn}
    """)
    UserViewVO getUserInfo(String userSn);

    // 更新用户
    @Update("UPDATE user SET userSn = #{userSn}, username = #{username}, password = #{password}, " +
            "nickname = #{nickname}, email = #{email}, phone = #{phone}, avatar = #{avatar}, " +
            "status = #{status}, role = #{role}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(User user);

    // 获取密码
    @Select("SELECT password FROM user WHERE userSn = #{userSn}")
    String getPWByUserSn(String userSn);

    // 修改密码
    @Update("UPDATE user SET password = #{newPw} WHERE userSn = #{userSn}")
    int UpdatePw(String userSn, String newPw);

}
