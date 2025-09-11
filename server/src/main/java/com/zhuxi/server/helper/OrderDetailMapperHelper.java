package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.OrderDetail;
import com.zhuxi.server.mapper.OrderDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDetailMapperHelper {
    
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    
    /**
     * 插入订单明细
     */
    public int insert(OrderDetail orderDetail) {
        return orderDetailMapper.insert(orderDetail);
    }
    
    /**
     * 根据ID删除订单明细
     */
    public int deleteById(Long id) {
        return orderDetailMapper.deleteById(id);
    }
    
    /**
     * 更新订单明细
     */
    public int update(OrderDetail orderDetail) {
        return orderDetailMapper.update(orderDetail);
    }
    
    /**
     * 根据ID查询订单明细
     */
    public OrderDetail selectById(Long id) {
        return orderDetailMapper.selectById(id);
    }
    
    /**
     * 根据订单ID查询订单明细列表
     */
    public List<OrderDetail> selectByOrderId(Long orderId) {
        return orderDetailMapper.selectByOrderId(orderId);
    }
    
    /**
     * 根据商品ID查询订单明细列表
     */
    public List<OrderDetail> selectByProductId(Long productId) {
        return orderDetailMapper.selectByProductId(productId);
    }
    
    /**
     * 根据卖家ID查询订单明细列表
     */
    public List<OrderDetail> selectBySellerId(Long sellerId) {
        return orderDetailMapper.selectBySellerId(sellerId);
    }
    
    /**
     * 查询所有订单明细
     */
    public List<OrderDetail> selectAll() {
        return orderDetailMapper.selectAll();
    }
    
    /**
     * 根据条件查询订单明细列表
     */
    public List<OrderDetail> selectByCondition(OrderDetail orderDetail) {
        return orderDetailMapper.selectByCondition(orderDetail);
    }
}
