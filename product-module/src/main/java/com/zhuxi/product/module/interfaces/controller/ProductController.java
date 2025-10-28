package com.zhuxi.product.module.interfaces.controller;

import com.zhuxi.common.shared.annotation.PermissionCheck;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.product.module.domain.service.ProductService;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.CategoryTreeVO;
import com.zhuxi.product.module.interfaces.vo.ConditionSHVO;
import com.zhuxi.product.module.interfaces.vo.ProductDetailVO;
import com.zhuxi.product.module.interfaces.vo.ShProductVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @GetMapping("/shProductList")
    @Operation(summary = "获取二手商品列表", description = "获取二手商品列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功", content = @Content(schema = @Schema(implementation = ShProductVO.class))),
            @ApiResponse(responseCode = "500", description = "获取失败")
    })
    public List<ShProductVO> getShProductList(@Valid ShProductParam shProductParam)
    {
        return productService.getShProductList(shProductParam);
    }

    @GetMapping("/shProductDetail")
    @Operation(summary = "获取二手商品详情", description = "获取二手商品详情")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功", content = @Content(schema = @Schema(implementation = ProductDetailVO.class))),
            @ApiResponse(responseCode = "500", description = "获取失败")
    })
    public ProductDetailVO getShProductDetail(@RequestParam String productId)
    {
        return productService.getProductDetail(productId);
    }


}
