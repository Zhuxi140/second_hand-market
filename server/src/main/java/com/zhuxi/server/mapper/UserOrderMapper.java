package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.UserOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserOrderMapper {
    // 插入订单
    @Insert("INSERT INTO user_order (orderSn, buyer_id, total_amount, status, receive_address_id, created_at, updated_at) " +
            "VALUES (#{orderSn}, #{buyerId}, #{totalAmount}, #{status}, #{receiveAddressId}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserOrder userOrder);
    
    // 根据ID删除订单
    @Delete("DELETE FROM user_order WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新订单
    @Update("UPDATE user_order SET orderSn = #{orderSn}, buyer_id = #{buyerId}, total_amount = #{totalAmount}, " +
            "status = #{status}, receive_address_id = #{receiveAddressId}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(UserOrder userOrder);
    
    // 根据ID查询订单
    @Select("SELECT * FROM user_order WHERE id = #{id}")
    UserOrder selectById(@Param("id") Long id);
    
    // 根据订单编号查询订单
    @Select("SELECT * FROM user_order WHERE orderSn = #{orderSn}")
    UserOrder selectByOrderSn(@Param("orderSn") String orderSn);
    
    // 根据买家ID查询订单列表
    @Select("SELECT * FROM user_order WHERE buyer_id = #{buyerId}")
    List<UserOrder> selectByBuyerId(@Param("buyerId") Long buyerId);
    
    // 根据订单状态查询订单列表
    @Select("SELECT * FROM user_order WHERE status = #{status}")
    List<UserOrder> selectByStatus(@Param("status") Integer status);
    
    // 查询所有订单
    @Select("SELECT * FROM user_order")
    List<UserOrder> selectAll();
    
    // 根据条件查询订单列表（复杂查询用XML）
    List<UserOrder> selectByCondition(UserOrder userOrder);
}
