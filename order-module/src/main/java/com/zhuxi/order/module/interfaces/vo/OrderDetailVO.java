package com.zhuxi.order.module.interfaces.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDetailVO {
    @Schema(description = "订单业务编号")
    private String orderSn;
    @Schema(description = "订单状态")
    private Integer status;
    @Schema(description = "买家业务编号")
    private String buyerSn;
    @Schema(description = "卖家业务编号")
    private String sellerSn;
    @Schema(description = "商品业务编号")
    private String productSn;
    @Schema(description = "订单金额")
    private BigDecimal amount;
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
