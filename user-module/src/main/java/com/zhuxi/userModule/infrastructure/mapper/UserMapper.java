package com.zhuxi.userModule.infrastructure.mapper;

import com.zhuxi.userModule.domain.user.model.User;
import com.zhuxi.userModule.interfaces.dto.user.UserRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdateInfoDTO;
import com.zhuxi.userModule.interfaces.vo.user.UserViewVO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // 通用save
    @Insert("""
            INSERT INTO user (userSn, username, password, nickname, phone, avatar,status, role,gender)
            VALUES (#{userSn}, #{username}, #{password}, #{nickname}, #{phone},#{avatar}, #{status}, #{role},#{gender})
    """)
    int save(User user);

    // 通用 update
    int update(User user);

    // 判断用户名是否存在
    @Select("SELECT 1 FROM user WHERE username = #{username}")
    Integer checkUsernameExist(String username);

    @Select("""
    SELECT
        id,
        userSn,
        password,
        nickname,
        status,
        role,
        avatar
    FROM user WHERE username = #{username}
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
        role,
        status,
        created_at
    FROM user WHERE userSn = #{userSn}
    """)
    UserViewVO getUserInfo(String userSn);

    @Select("""
    SELECT
        id,
        status,
        role
    FROM user WHERE userSn = #{userSn}
    """)
    User getISBySn(String userSn);

    // 修改密码
    @Update("UPDATE user SET password = #{newPw} WHERE id = #{id}")
    int updatePw(Long id, String newPw);

}
