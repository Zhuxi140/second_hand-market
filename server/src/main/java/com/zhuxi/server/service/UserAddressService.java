package com.zhuxi.server.service;

import com.zhuxi.pojo.entity.UserAddress;

import java.util.List;

public interface UserAddressService {
    // 插入地址
    int insert(UserAddress userAddress);
    
    // 根据ID删除地址
    int deleteById(Long id);
    
    // 更新地址
    int update(UserAddress userAddress);
    
    // 根据ID查询地址
    UserAddress selectById(Long id);
    
    // 根据用户ID查询地址列表
    List<UserAddress> selectByUserId(Long userId);
    
    // 根据用户ID查询默认地址
    UserAddress selectDefaultByUserId(Long userId);
    
    // 查询所有地址
    List<UserAddress> selectAll();
    
    // 根据条件查询地址列表
    List<UserAddress> selectByCondition(UserAddress userAddress);
}
