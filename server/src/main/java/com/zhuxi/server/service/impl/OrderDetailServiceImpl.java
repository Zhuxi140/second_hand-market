package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.OrderDetail;
import com.zhuxi.server.helper.OrderDetailMapperHelper;
import com.zhuxi.server.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    
    @Autowired
    private OrderDetailMapperHelper orderDetailMapperHelper;
    
    /**
     * 插入订单明细
     */
    @Override
    public int insert(OrderDetail orderDetail) {
        return orderDetailMapperHelper.insert(orderDetail);
    }
    
    /**
     * 根据ID删除订单明细
     */
    @Override
    public int deleteById(Long id) {
        return orderDetailMapperHelper.deleteById(id);
    }
    
    /**
     * 更新订单明细
     */
    @Override
    public int update(OrderDetail orderDetail) {
        return orderDetailMapperHelper.update(orderDetail);
    }
    
    /**
     * 根据ID查询订单明细
     */
    @Override
    public OrderDetail selectById(Long id) {
        return orderDetailMapperHelper.selectById(id);
    }
    
    /**
     * 根据订单ID查询订单明细列表
     */
    @Override
    public List<OrderDetail> selectByOrderId(Long orderId) {
        return orderDetailMapperHelper.selectByOrderId(orderId);
    }
    
    /**
     * 根据商品ID查询订单明细列表
     */
    @Override
    public List<OrderDetail> selectByProductId(Long productId) {
        return orderDetailMapperHelper.selectByProductId(productId);
    }
    
    /**
     * 根据卖家ID查询订单明细列表
     */
    @Override
    public List<OrderDetail> selectBySellerId(Long sellerId) {
        return orderDetailMapperHelper.selectBySellerId(sellerId);
    }
    
    /**
     * 查询所有订单明细
     */
    @Override
    public List<OrderDetail> selectAll() {
        return orderDetailMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询订单明细列表
     */
    @Override
    public List<OrderDetail> selectByCondition(OrderDetail orderDetail) {
        return orderDetailMapperHelper.selectByCondition(orderDetail);
    }
}

