package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.UserOrder;
import com.zhuxi.server.helper.UserOrderMapperHelper;
import com.zhuxi.server.service.Service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    
    @Autowired
    private UserOrderMapperHelper userOrderMapperHelper;
    
    /**
     * 插入订单
     */
    @Override
    public int insert(UserOrder userOrder) {
        return userOrderMapperHelper.insert(userOrder);
    }
    
    /**
     * 根据ID删除订单
     */
    @Override
    public int deleteById(Long id) {
        return userOrderMapperHelper.deleteById(id);
    }
    
    /**
     * 更新订单
     */
    @Override
    public int update(UserOrder userOrder) {
        return userOrderMapperHelper.update(userOrder);
    }
    
    /**
     * 根据ID查询订单
     */
    @Override
    public UserOrder selectById(Long id) {
        return userOrderMapperHelper.selectById(id);
    }
    
    /**
     * 根据订单编号查询订单
     */
    @Override
    public UserOrder selectByOrderSn(String orderSn) {
        return userOrderMapperHelper.selectByOrderSn(orderSn);
    }
    
    /**
     * 根据买家ID查询订单列表
     */
    @Override
    public List<UserOrder> selectByBuyerId(Long buyerId) {
        return userOrderMapperHelper.selectByBuyerId(buyerId);
    }
    
    /**
     * 查询所有订单
     */
    @Override
    public List<UserOrder> selectAll() {
        return userOrderMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询订单列表
     */
    @Override
    public List<UserOrder> selectByCondition(UserOrder userOrder) {
        return userOrderMapperHelper.selectByCondition(userOrder);
    }
}
