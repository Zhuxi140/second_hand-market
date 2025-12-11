package com.zhuxi.order.module.domain.module;

import com.zhuxi.order.module.domain.enums.OrderStatus;
import com.zhuxi.order.module.domain.objectValue.OrderDetail;
import com.zhuxi.order.module.interfaces.param.CreateOrderParam;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author zhuxi
 */

@Getter
public class Order {

    private Long id;
    private String orderSn;
    private Long buyerId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private Long receiverAddressId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderDetail orderDetail;

    private Order(Long id, String orderSn, Long buyerId, BigDecimal totalAmount, OrderStatus status, Long receiverAddressId, LocalDateTime createdAt, LocalDateTime updatedAt, OrderDetail orderDetail) {
        this.id = id;
        this.orderSn = orderSn;
        this.buyerId = buyerId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.receiverAddressId = receiverAddressId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orderDetail = orderDetail;
    }

    public Order() {
    }

    /**
     * 创建订单
     * @param detail 订单详情
     * @param param 创建订单参数
     */
    public static Order create(OrderDetail detail, CreateOrderParam param){
        Order order = new Order();
        order.orderSn = UUID.randomUUID().toString().replace("-", "");
        order.buyerId = param.getBuyerId();
        order.totalAmount = param.getTotalAmount();
        order.receiverAddressId = param.getReceiverAddressId();
        order.status = OrderStatus.PENDING_PAYMENT;
        order.orderDetail = detail;

        return order;
    }
}
