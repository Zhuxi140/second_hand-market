package com.zhuxi.product.module.interfaces.controller;

import com.zhuxi.product.module.domain.service.ProductService;
import com.zhuxi.product.module.interfaces.vo.CategoryTreeVO;
import com.zhuxi.product.module.interfaces.vo.ConditionSHVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhuxi
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "商品通用接口", description = "商品相关接口，不限制权限的商品接口")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/categories")
    @Operation(summary = "获取商品分类列表", description = "获取商品分类列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功", content = @Content(schema = @Schema(implementation = CategoryTreeVO.class))),
            @ApiResponse(responseCode = "500", description = "获取失败")
    })
    public List<CategoryTreeVO> getCategoryList(@RequestParam(defaultValue = "100") int limit,@RequestParam(defaultValue = "0") int offset)
    {
        return productService.getCategoryList(limit, offset);
    }

    @GetMapping("/conditions")
    @Operation(summary = "获取二手商品成色列表", description = "获取二手商品成色列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功", content = @Content(schema = @Schema(implementation = ConditionSHVO.class))),
            @ApiResponse(responseCode = "500", description = "获取失败")
    })
    public List<ConditionSHVO> getConditions()
    {
        return productService.getShConditions();
    }

}
