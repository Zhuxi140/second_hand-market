package com.zhuxi.order.module.infrastructure.mapper;

import com.zhuxi.order.module.domain.module.Order;
import com.zhuxi.order.module.domain.objectValue.OrderDetail;
import com.zhuxi.order.module.domain.objectValue.ProductInfo;
import com.zhuxi.order.module.interfaces.vo.OrderDetailVO;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单MyBatis映射接口
 * 定义订单相关的增删改查SQL方法
 * @author zhuxi
 */
@Mapper
public interface OrderMapper {

    /**
     * 插入订单记录
     */
    int insertOrder(Order order);

    /**
     * 插入订单详情记录
     */
    int insertOrderDetail(OrderDetail order);


    /**
     * 根据用户编号查询用户ID
     */
    @Select("SELECT id FROM user WHERE userSn = #{sn}")
    Long selectUserIdBySn(String sn);

    /**
     * 根据商品编号查询商品ID
     */
    @Select("SELECT id FROM product WHERE product_sn = #{sn}")
    Long selectProductIdBySn(String sn);


    /**
     * 根据地址编号查询地址ID
     */
    @Select("SELECT id FROM user_address WHERE addressSn = #{sn}")
    Long selectAddressIdBySn(String sn);

    @Select("SELECT id FROM product_spec WHERE sku_sn = #{skuSn}")
    Long getSpecIdBySkuSn(String skuSn);

    ProductInfo getProductInfoAndSpec(Long productId,String skuSn);

    @Select("SELECT title,description,price FROM product WHERE id = #{productId}")
    ProductInfo getProductInfo(Long productId);

    /**
     * 查询订单详情视图
     */
    @Select("""
    SELECT o.order_sn, o.status, b.userSn AS buyerSn, s.userSn AS sellerSn, p.product_sn AS productSn, o.amount, o.created_at
    FROM orders o
    JOIN user b ON o.buyer_id = b.id
    JOIN user s ON o.seller_id = s.id
    JOIN sh_product p ON o.product_id = p.id
    WHERE o.order_sn = #{orderSn}
    """)
    OrderDetailVO selectDetail(String orderSn);

    /**
     * 更新订单状态与备注
     */
    @Update("UPDATE orders SET status = #{status}, remark = #{remark} WHERE order_sn = #{orderSn}")
    int updateStatus(String orderSn, Integer status, String remark);

    /**
     * 分页查询用户关联的订单列表
     */
    @Select("""
    SELECT o.order_sn, o.status, b.userSn AS buyerSn, s.userSn AS sellerSn, p.product_sn AS productSn, o.amount, o.created_at
    FROM orders o
    JOIN user b ON o.buyer_id = b.id
    JOIN user s ON o.seller_id = s.id
    JOIN sh_product p ON o.product_id = p.id
    WHERE b.userSn = #{userSn} OR s.userSn = #{userSn}
    ORDER BY o.created_at DESC
    LIMIT #{limit} OFFSET #{offset}
    """)
    List<OrderDetailVO> selectListByUserSn(String userSn, int limit, int offset);
}
