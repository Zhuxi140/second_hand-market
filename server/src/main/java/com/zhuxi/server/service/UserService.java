package com.zhuxi.server.service;

import com.zhuxi.pojo.entity.User;

import java.util.List;

public interface UserService {
    // 插入用户
    int insert(User user);
    
    // 根据ID删除用户
    int deleteById(Long id);
    
    // 更新用户
    int update(User user);
    
    // 根据ID查询用户
    User selectById(Long id);
    
    // 根据用户名查询用户
    User selectByUsername(String username);
    
    // 根据手机号查询用户
    User selectByPhone(String phone);
    
    // 查询所有用户
    List<User> selectAll();
    
    // 根据条件查询用户列表
    List<User> selectByCondition(User user);
}
