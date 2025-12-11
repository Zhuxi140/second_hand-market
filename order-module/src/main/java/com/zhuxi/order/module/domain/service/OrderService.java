package com.zhuxi.order.module.domain.service;

import com.zhuxi.order.module.interfaces.dto.CancelOrderDTO;
import com.zhuxi.order.module.interfaces.dto.CreateOrderDTO;
import com.zhuxi.order.module.interfaces.dto.UpdateOrderStatusDTO;
import com.zhuxi.order.module.interfaces.vo.OrderDetailVO;

import java.util.List;

/**
 * @author zhuxi
 */
public interface OrderService {



    String create(CreateOrderDTO dto,String userSn);

    /**
     * 获取订单详情
     */
    OrderDetailVO detail(String orderSn);

    /**
     * 更新订单状态
     */
    void updateStatus(String orderSn, UpdateOrderStatusDTO dto);

    /**
     * 获取用户订单列表
     */
    List<OrderDetailVO> list(String userSn, int page, int size);

    /**
     * 取消订单
     */
    void cancel(String orderSn, CancelOrderDTO dto);
}
