package com.zhuxi.order.module.domain.service;

import com.zhuxi.order.module.domain.module.Order;
import com.zhuxi.order.module.domain.objectValue.ProductInfo;
import com.zhuxi.order.module.interfaces.vo.OrderDetailVO;
import java.util.List;

/**
 * 订单领域仓储接口
 * 抽象订单相关的数据访问操作
 * @author zhuxi
 */
public interface OrderRepository {

    /**
     * 插入订单记录
     */
    boolean insert(Order order);

    /**
     * 根据用户编号获取用户ID
     */
    Long getUserIdBySn(String sn);

    /**
     * 根据商品编号获取商品ID
     */
    Long getProductIdBySn(String sn);

    /**
     * 根据地址编号获取地址ID
     */
    Long getAddressIdBySn(String sn);

   /**
     * 获取商品信息及规格
     */
    ProductInfo getProductInAndSpec(Long productId,String skuSn);

    /**
     * 获取订单详情视图
     */
    OrderDetailVO getDetail(String orderSn);

    /**
     * 更新订单状态与备注
     */
    void updateStatus(String orderSn, Integer status, String remark);

    /**
     * 根据用户编号获取其关联订单列表
     */
    List<OrderDetailVO> getListByUserSn(String userSn, int limit, int offset);
}
