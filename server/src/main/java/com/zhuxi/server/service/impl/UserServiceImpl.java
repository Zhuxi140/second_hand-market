package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.User;
import com.zhuxi.server.helper.UserMapperHelper;
import com.zhuxi.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapperHelper userMapperHelper;
    
    /**
     * 插入用户
     */
    @Override
    public int insert(User user) {
        return userMapperHelper.insert(user);
    }
    
    /**
     * 根据ID删除用户
     */
    @Override
    public int deleteById(Long id) {
        return userMapperHelper.deleteById(id);
    }
    
    /**
     * 更新用户
     */
    @Override
    public int update(User user) {
        return userMapperHelper.update(user);
    }
    
    /**
     * 根据ID查询用户
     */
    @Override
    public User selectById(Long id) {
        return userMapperHelper.selectById(id);
    }
    
    /**
     * 根据用户名查询用户
     */
    @Override
    public User selectByUsername(String username) {
        return userMapperHelper.selectByUsername(username);
    }
    
    /**
     * 根据手机号查询用户
     */
    @Override
    public User selectByPhone(String phone) {
        return userMapperHelper.selectByPhone(phone);
    }
    
    /**
     * 查询所有用户
     */
    @Override
    public List<User> selectAll() {
        return userMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询用户列表
     */
    @Override
    public List<User> selectByCondition(User user) {
        return userMapperHelper.selectByCondition(user);
    }
}
