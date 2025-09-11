package com.zhuxi.server.service;

import com.zhuxi.pojo.entity.UserOrder;

import java.util.List;

public interface UserOrderService {
    // 插入订单
    int insert(UserOrder userOrder);
    
    // 根据ID删除订单
    int deleteById(Long id);
    
    // 更新订单
    int update(UserOrder userOrder);
    
    // 根据ID查询订单
    UserOrder selectById(Long id);
    
    // 根据订单编号查询订单
    UserOrder selectByOrderSn(String orderSn);
    
    // 根据买家ID查询订单列表
    List<UserOrder> selectByBuyerId(Long buyerId);
    
    // 查询所有订单
    List<UserOrder> selectAll();
    
    // 根据条件查询订单列表
    List<UserOrder> selectByCondition(UserOrder userOrder);
}
