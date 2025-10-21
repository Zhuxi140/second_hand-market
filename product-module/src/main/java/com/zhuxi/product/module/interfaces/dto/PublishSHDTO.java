package com.zhuxi.product.module.interfaces.dto;

import com.zhuxi.common.constant.ValidationMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author zhuxi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "发布二手商品DTO")
public class PublishSHDTO {
    @Schema(description = "商品标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL_NOT_EMPTY)
    @Size(max = 20, message = "标题长度不能超过20个字符")
    private String title;

    @Schema(description = "商品描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL_NOT_EMPTY)
    private String description;

    @Schema(description = "商品分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = ValidationMessage.NOT_NULL)
    @Positive(message = ValidationMessage.NOT_LESS_ZERO)
    private Long categoryId;

    @Schema(description = "商品价格", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = ValidationMessage.NOT_NULL)
    @Positive(message = ValidationMessage.NOT_LESS_ZERO)
    private BigDecimal price;

    @Schema(description = "成色类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = ValidationMessage.NOT_NULL)
    @Positive(message = ValidationMessage.NOT_LESS_ZERO)
    private Integer conditionId;

    @Schema(description = "地点",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String location;

    @Schema(description = "是否为草稿 1为草稿 0为非草稿",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotNull(message = ValidationMessage.NOT_NULL)
    private Integer isDraft;

}
