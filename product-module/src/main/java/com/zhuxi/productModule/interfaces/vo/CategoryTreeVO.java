package com.zhuxi.productModule.interfaces.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuxi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商品分类树形结构VO")
public class CategoryTreeVO extends CategoryVO{
    @Schema(description = "子分类列表")
    private List<CategoryTreeVO> children;

    public CategoryTreeVO(CategoryVO categoryVO) {
        this.setId(categoryVO.getId());
        this.setName(categoryVO.getName());
        this.setParentId(categoryVO.getParentId());
        this.setIconUrl(categoryVO.getIconUrl());
        this.children = new ArrayList<>();
    }
}
