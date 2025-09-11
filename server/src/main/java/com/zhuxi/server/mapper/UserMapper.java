package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    // 插入用户
    @Insert("INSERT INTO user (userSn, username, password, nickname, email, phone, avatar, status, role, created_at, updated_at) " +
            "VALUES (#{userSn}, #{username}, #{password}, #{nickname}, #{email}, #{phone}, #{avatar}, #{status}, #{role}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
    
    // 根据ID删除用户
    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新用户
    @Update("UPDATE user SET userSn = #{userSn}, username = #{username}, password = #{password}, " +
            "nickname = #{nickname}, email = #{email}, phone = #{phone}, avatar = #{avatar}, " +
            "status = #{status}, role = #{role}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(User user);
    
    // 根据ID查询用户
    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(@Param("id") Long id);
    
    // 根据用户名查询用户
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(@Param("username") String username);
    
    // 根据手机号查询用户
    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User selectByPhone(@Param("phone") String phone);
    
    // 根据邮箱查询用户
    @Select("SELECT * FROM user WHERE email = #{email}")
    User selectByEmail(@Param("email") String email);
    
    // 查询所有用户
    @Select("SELECT * FROM user")
    List<User> selectAll();
    
    // 根据条件查询用户列表（复杂查询用XML）
    List<User> selectByCondition(User user);
}
