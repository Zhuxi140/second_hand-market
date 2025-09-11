package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.User;
import com.zhuxi.server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapperHelper {
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 插入用户
     */
    public int insert(User user) {
        return userMapper.insert(user);
    }
    
    /**
     * 根据ID删除用户
     */
    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }
    
    /**
     * 更新用户
     */
    public int update(User user) {
        return userMapper.update(user);
    }
    
    /**
     * 根据ID查询用户
     */
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }
    
    /**
     * 根据用户名查询用户
     */
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
    
    /**
     * 根据手机号查询用户
     */
    public User selectByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }
    
    /**
     * 根据邮箱查询用户
     */
    public User selectByEmail(String email) {
        return userMapper.selectByEmail(email);
    }
    
    /**
     * 查询所有用户
     */
    public List<User> selectAll() {
        return userMapper.selectAll();
    }
    
    /**
     * 根据条件查询用户列表
     */
    public List<User> selectByCondition(User user) {
        return userMapper.selectByCondition(user);
    }
}
