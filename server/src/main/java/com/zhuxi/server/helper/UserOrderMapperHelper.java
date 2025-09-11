package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.UserOrder;
import com.zhuxi.server.mapper.UserOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserOrderMapperHelper {
    
    @Autowired
    private UserOrderMapper userOrderMapper;
    
    /**
     * 插入订单
     */
    public int insert(UserOrder userOrder) {
        return userOrderMapper.insert(userOrder);
    }
    
    /**
     * 根据ID删除订单
     */
    public int deleteById(Long id) {
        return userOrderMapper.deleteById(id);
    }
    
    /**
     * 更新订单
     */
    public int update(UserOrder userOrder) {
        return userOrderMapper.update(userOrder);
    }
    
    /**
     * 根据ID查询订单
     */
    public UserOrder selectById(Long id) {
        return userOrderMapper.selectById(id);
    }
    
    /**
     * 根据订单编号查询订单
     */
    public UserOrder selectByOrderSn(String orderSn) {
        return userOrderMapper.selectByOrderSn(orderSn);
    }
    
    /**
     * 根据买家ID查询订单列表
     */
    public List<UserOrder> selectByBuyerId(Long buyerId) {
        return userOrderMapper.selectByBuyerId(buyerId);
    }
    
    /**
     * 根据订单状态查询订单列表
     */
    public List<UserOrder> selectByStatus(Integer status) {
        return userOrderMapper.selectByStatus(status);
    }
    
    /**
     * 查询所有订单
     */
    public List<UserOrder> selectAll() {
        return userOrderMapper.selectAll();
    }
    
    /**
     * 根据条件查询订单列表
     */
    public List<UserOrder> selectByCondition(UserOrder userOrder) {
        return userOrderMapper.selectByCondition(userOrder);
    }
}
