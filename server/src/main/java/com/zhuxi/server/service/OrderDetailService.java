package com.zhuxi.server.service;

import com.zhuxi.pojo.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    
    /**
     * 插入订单明细
     */
    int insert(OrderDetail orderDetail);
    
    /**
     * 根据ID删除订单明细
     */
    int deleteById(Long id);
    
    /**
     * 更新订单明细
     */
    int update(OrderDetail orderDetail);
    
    /**
     * 根据ID查询订单明细
     */
    OrderDetail selectById(Long id);
    
    /**
     * 根据订单ID查询订单明细列表
     */
    List<OrderDetail> selectByOrderId(Long orderId);
    
    /**
     * 根据商品ID查询订单明细列表
     */
    List<OrderDetail> selectByProductId(Long productId);
    
    /**
     * 根据卖家ID查询订单明细列表
     */
    List<OrderDetail> selectBySellerId(Long sellerId);
    
    /**
     * 查询所有订单明细
     */
    List<OrderDetail> selectAll();
    
    /**
     * 根据条件查询订单明细列表
     */
    List<OrderDetail> selectByCondition(OrderDetail orderDetail);
}
