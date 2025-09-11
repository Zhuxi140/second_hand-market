package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.UserAddress;
import com.zhuxi.server.mapper.UserAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAddressMapperHelper {
    
    @Autowired
    private UserAddressMapper userAddressMapper;
    
    /**
     * 插入地址
     */
    public int insert(UserAddress userAddress) {
        return userAddressMapper.insert(userAddress);
    }
    
    /**
     * 根据ID删除地址
     */
    public int deleteById(Long id) {
        return userAddressMapper.deleteById(id);
    }
    
    /**
     * 更新地址
     */
    public int update(UserAddress userAddress) {
        return userAddressMapper.update(userAddress);
    }
    
    /**
     * 根据ID查询地址
     */
    public UserAddress selectById(Long id) {
        return userAddressMapper.selectById(id);
    }
    
    /**
     * 根据用户ID查询地址列表
     */
    public List<UserAddress> selectByUserId(Long userId) {
        return userAddressMapper.selectByUserId(userId);
    }
    
    /**
     * 根据用户ID查询默认地址
     */
    public UserAddress selectDefaultByUserId(Long userId) {
        return userAddressMapper.selectDefaultByUserId(userId);
    }
    
    /**
     * 查询所有地址
     */
    public List<UserAddress> selectAll() {
        return userAddressMapper.selectAll();
    }
    
    /**
     * 根据条件查询地址列表
     */
    public List<UserAddress> selectByCondition(UserAddress userAddress) {
        return userAddressMapper.selectByCondition(userAddress);
    }
}
