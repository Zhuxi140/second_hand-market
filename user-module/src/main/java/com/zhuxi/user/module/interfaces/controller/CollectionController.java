package com.zhuxi.user.module.interfaces.controller;

import com.zhuxi.common.interfaces.result.Result;
import com.zhuxi.common.shared.annotation.PermissionCheck;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.user.module.domain.collection.service.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "收藏管理", description = "用户收藏相关接口")
@RestController
@RequestMapping("/user/me")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CollectionController {

    private final CollectionService service;

    @Operation(summary = "收藏商品", description = "收藏指定商品")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功"),
            @ApiResponse(responseCode = "500", description = "失败")
    })
    @PostMapping("/{userSn}/collections")
    @PermissionCheck(Role = Role.user, permission = "user:addCollection", enableDataOwnership = true)
    public Result<String> add(@PathVariable String userSn, @RequestBody String productSn){
        service.add(userSn, productSn);
        return Result.success();
    }

    @Operation(summary = "取消收藏", description = "取消收藏指定商品")
    @DeleteMapping("/{userSn}/collections/{productSn}")
    @PermissionCheck(Role = Role.user, permission = "user:delCollection", enableDataOwnership = true)
    public Result<String> del(@PathVariable String userSn, @PathVariable String productSn){
        service.del(userSn, productSn);
        return Result.success();
    }

    @Operation(summary = "获取收藏列表", description = "获取用户收藏的商品列表")
    @GetMapping("/{userSn}/collections")
    @PermissionCheck(Role = Role.user, permission = "user:getCollections", enableDataOwnership = true)
    public Result<List<Map<String,Object>>> list(@PathVariable String userSn, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size){
        List<Map<String,Object>> list = service.list(userSn, page, size);
        return Result.success(list);
    }
}
