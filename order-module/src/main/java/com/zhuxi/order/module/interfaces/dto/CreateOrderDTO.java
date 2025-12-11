package com.zhuxi.order.module.interfaces.dto;

import com.zhuxi.common.shared.constant.ValidationMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author zhuxi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO {
    @Schema(description = "卖家业务编号")
    @NotBlank(message = ValidationMessage.NOT_NULL_NOT_EMPTY)
    private String sellerSn;
    @Schema(description = "商品业务编号")
    @NotBlank(message = ValidationMessage.NOT_NULL_NOT_EMPTY)
    private String productSn;
    @Schema(description = "商品规格业务编号")
    private String skuSn;
    @Schema(description = "数量")
    @NotNull(message = ValidationMessage.NOT_NULL)
    @Positive(message = ValidationMessage.NOT_LESS_ZERO)
    private Integer quantity;
    @Schema(description = "地址业务编号")
    @NotBlank(message = ValidationMessage.NOT_NULL_NOT_EMPTY)
    private String addressSn;
    @Schema(description = "是否为二手商品订单")
    @NotNull(message = ValidationMessage.NOT_NULL)
    private boolean isSh;

}
