package com.zhuxi.product.module.interfaces.controller;

import com.zhuxi.common.interfaces.result.Result;
import com.zhuxi.common.shared.annotation.PermissionCheck;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.product.module.domain.service.ProductService;
import com.zhuxi.product.module.interfaces.dto.UpdateProductDTO;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.*;
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
    public ProductDetailVO getShProductDetail(@RequestParam String productSn)
    {
        return productService.getShProductDetail(productSn);
    }

    @PostMapping("/updateProduct")
    @Operation(summary = "修改商品", description = "修改商品")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "修改成功"),
            @ApiResponse(responseCode = "500", description = "修改失败")
    })
    @PermissionCheck(Role = Role.user,permission = "user:updateProductInfo", enableDataOwnership = true)
    public Result<String> updateProduct(@Valid @RequestBody UpdateProductDTO update, @RequestParam String userSn)
    {
        productService.updateProduct(update, userSn);
        return  Result.success();
    }

    @PostMapping("/delProduct")
    @Operation(summary = "删除商品", description = "删除商品")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "500", description = "删除失败")
    })
    @PermissionCheck(Role = Role.user,permission = "user:delProduct", enableDataOwnership = true)
    public Result<String> delProduct(@RequestParam String productSn,@RequestParam String userSn)
    {
        productService.delProduct(productSn);
        return  Result.success();
    }

    @GetMapping("/meShProductList")
    @Operation(summary = "获取个人发布商品列表", description = "获取个人发布商品列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功", content = @Content(schema = @Schema(implementation = MeShProductVO.class))),
            @ApiResponse(responseCode = "500", description = "获取失败")
    })
    @PermissionCheck(Role = Role.user,permission = "user:getMeShProductList", enableDataOwnership = true)
    public List<MeShProductVO> getMeShProductList(@RequestParam String userSn)
    {
        return productService.getMeShProductList(userSn);
    }


}
