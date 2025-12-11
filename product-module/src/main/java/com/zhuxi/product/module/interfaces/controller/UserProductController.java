package com.zhuxi.product.module.interfaces.controller;

import com.zhuxi.product.module.domain.service.ProductService;
import com.zhuxi.product.module.interfaces.dto.PublishSHDTO;
import com.zhuxi.common.interfaces.result.Result;
import com.zhuxi.common.shared.annotation.PermissionCheck;
import com.zhuxi.common.shared.enums.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuxi
 */
@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "用户个人商品管理", description = "用户个人商品相关接口，限制使用权限")
@RequestMapping("/user/me")
public class UserProductController {

    private final ProductService productService;

    @GetMapping("/publish")
    @Operation(summary = "发布二手商品", description = "发布二手商品")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "发布成功"),
            @ApiResponse(responseCode = "500", description = "发布失败: 商品标题、描述等不能为空, 发布失败，稍后重试等")
    })
    public String publish(@RequestBody PublishSHDTO sh, @RequestParam String userSn){
        return productService.publishSh(sh, userSn);
    }

    @PostMapping("/{userSn}/products")
    @Operation(summary = "发布二手商品", description = "发布二手商品")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "发布成功"),
            @ApiResponse(responseCode = "500", description = "发布失败")
    })
    @PermissionCheck(Role = Role.user, permission = "user:publishProduct", enableDataOwnership = true)
    public Result<String> publishProduct(@RequestBody PublishSHDTO sh, @PathVariable String userSn){
        String sn = productService.publishSh(sh, userSn);
        return Result.success(sn);
    }
}
