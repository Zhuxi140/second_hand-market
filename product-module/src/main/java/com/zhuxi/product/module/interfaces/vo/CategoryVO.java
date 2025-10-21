package com.zhuxi.product.module.interfaces.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuxi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商品分类列表名VO")
public class CategoryVO {

    @Schema(description = "商品分类id")
    private Long id;
    @Schema(description = "商品分类名称")
    private String name;
    @Schema(description = "商品分类父级id")
    private Long parentId;
    @Schema(description = "商品分类图标url")
    private String iconUrl;
}
