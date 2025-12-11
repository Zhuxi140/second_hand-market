package com.zhuxi.order.module.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateOrderStatusDTO {
    @Schema(description = "订单状态")
    private Integer userStatus;
    @Schema(description = "备注")
    private String remark;
}
