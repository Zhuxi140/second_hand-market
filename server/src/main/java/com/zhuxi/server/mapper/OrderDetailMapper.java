package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.OrderDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    // 插入订单明细
    @Insert("INSERT INTO order_detail (order_id, product_id, sku_id, seller_id, quantity, unit_price, product_snapshot, created_at, updated_at) " +
            "VALUES (#{orderId}, #{productId}, #{skuId}, #{sellerId}, #{quantity}, #{unitPrice}, #{productSnapshot}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OrderDetail orderDetail);
    
    // 根据ID删除订单明细
    @Delete("DELETE FROM order_detail WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新订单明细
    @Update("UPDATE order_detail SET order_id = #{orderId}, product_id = #{productId}, sku_id = #{skuId}, " +
            "seller_id = #{sellerId}, quantity = #{quantity}, unit_price = #{unitPrice}, product_snapshot = #{productSnapshot}, " +
            "updated_at = #{updatedAt} WHERE id = #{id}")
    int update(OrderDetail orderDetail);
    
    // 根据ID查询订单明细
    @Select("SELECT * FROM order_detail WHERE id = #{id}")
    OrderDetail selectById(@Param("id") Long id);
    
    // 根据订单ID查询订单明细列表
    @Select("SELECT * FROM order_detail WHERE order_id = #{orderId}")
    List<OrderDetail> selectByOrderId(@Param("orderId") Long orderId);
    
    // 根据商品ID查询订单明细列表
    @Select("SELECT * FROM order_detail WHERE product_id = #{productId}")
    List<OrderDetail> selectByProductId(@Param("productId") Long productId);
    
    // 根据卖家ID查询订单明细列表
    @Select("SELECT * FROM order_detail WHERE seller_id = #{sellerId}")
    List<OrderDetail> selectBySellerId(@Param("sellerId") Long sellerId);
    
    // 查询所有订单明细
    @Select("SELECT * FROM order_detail")
    List<OrderDetail> selectAll();
    
    // 根据条件查询订单明细列表（复杂查询用XML）
    List<OrderDetail> selectByCondition(OrderDetail orderDetail);
}
