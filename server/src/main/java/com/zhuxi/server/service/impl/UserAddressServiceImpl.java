package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.UserAddress;
import com.zhuxi.server.helper.UserAddressMapperHelper;
import com.zhuxi.server.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {
    
    @Autowired
    private UserAddressMapperHelper userAddressMapperHelper;
    
    /**
     * 插入地址
     */
    @Override
    public int insert(UserAddress userAddress) {
        return userAddressMapperHelper.insert(userAddress);
    }
    
    /**
     * 根据ID删除地址
     */
    @Override
    public int deleteById(Long id) {
        return userAddressMapperHelper.deleteById(id);
    }
    
    /**
     * 更新地址
     */
    @Override
    public int update(UserAddress userAddress) {
        return userAddressMapperHelper.update(userAddress);
    }
    
    /**
     * 根据ID查询地址
     */
    @Override
    public UserAddress selectById(Long id) {
        return userAddressMapperHelper.selectById(id);
    }
    
    /**
     * 根据用户ID查询地址列表
     */
    @Override
    public List<UserAddress> selectByUserId(Long userId) {
        return userAddressMapperHelper.selectByUserId(userId);
    }
    
    /**
     * 根据用户ID查询默认地址
     */
    @Override
    public UserAddress selectDefaultByUserId(Long userId) {
        return userAddressMapperHelper.selectDefaultByUserId(userId);
    }
    
    /**
     * 查询所有地址
     */
    @Override
    public List<UserAddress> selectAll() {
        return userAddressMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询地址列表
     */
    @Override
    public List<UserAddress> selectByCondition(UserAddress userAddress) {
        return userAddressMapperHelper.selectByCondition(userAddress);
    }
}
