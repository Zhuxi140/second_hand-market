package com.zhuxi.order.module.interfaces.param;

import com.zhuxi.order.module.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author zhuxi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderParam {
    private Long buyerId;
    private BigDecimal totalAmount;
    private Long receiverAddressId;
}
