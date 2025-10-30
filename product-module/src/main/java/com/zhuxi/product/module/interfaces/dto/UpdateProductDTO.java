package com.zhuxi.product.module.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhuxi.common.shared.constant.ValidationMessage;
import com.zhuxi.product.module.domain.enums.IsDraft;
import com.zhuxi.product.module.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@Schema(description = "修改商品请求参数")
public class UpdateProductDTO {

    @Schema(description = "商品编号",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL_NOT_EMPTY)
    private String productSn;
    @JsonIgnore
    private Long shopId;
    @Schema(description = "商品名称",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String title;
    @Schema(description = "商品描述",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;
    @Schema(description = "商品分类id",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Min(value = 0L,message = ValidationMessage.NOT_LESS_ZERO)
    private Long categoryId;
    @Schema(description = "商品价格",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @DecimalMin(value = "0",inclusive = false,message = ValidationMessage.NOT_LESS_ZERO)
    private BigDecimal price;
    @Schema(description = "商品成色id",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Min(value = 0L,message = ValidationMessage.NOT_LESS_ZERO)
    private Integer conditionId;
    @Schema(description = "商品状态",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = "1|0|null",message = "status参数格式错误")
    private Integer status;
    @Schema(description = "商品所在地区",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String location;
    @Schema(description = "商品是否为草稿状态(1为草稿，0为发布)",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = "1|0|null",message = "isDraft参数格式错误")
    private IsDraft isDraft;
}
