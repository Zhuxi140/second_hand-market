package com.zhuxi.product.module.interfaces.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuxi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "商品图片列表")
public class ProductImages {
    @Schema(description = "规格id")
    private Long skuId;
    @Schema(description = "图片地址")
    private String imageUrl;
    @Schema(description = "图片类型")
    private Integer type;
    @Schema(description = "排序依据数值（值越小越靠前）")
    private Integer sortOrder;
}
