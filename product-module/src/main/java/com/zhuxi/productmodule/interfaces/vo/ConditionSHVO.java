package com.zhuxi.productmodule.interfaces.vo;

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
@Schema(description = "二手商品成色列表视图")
public class ConditionSHVO {

    @Schema(description = "成色编号", example = "1")
    private Long id;
    @Schema(description = "成色名称", example = "x成新")
    private String name;
}
