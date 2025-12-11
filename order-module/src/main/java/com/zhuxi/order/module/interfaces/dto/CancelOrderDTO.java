package com.zhuxi.order.module.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CancelOrderDTO {
    @Schema(description = "取消原因")
    private String reason;
}
