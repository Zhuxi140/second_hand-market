package com.zhuxi.user.module.interfaces.controller;


import com.zhuxi.common.shared.annotation.PermissionCheck;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.constant.AuthMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.interfaces.result.Result;
import com.zhuxi.common.interfaces.result.TokenResult;
import com.zhuxi.common.shared.utils.JwtUtils;
import com.zhuxi.user.module.application.assembler.UserConvert;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.service.UserService;
import com.zhuxi.user.module.domain.user.valueObject.RefreshToken;
import com.zhuxi.user.module.interfaces.dto.user.*;
import com.zhuxi.user.module.interfaces.vo.user.UserLoginVO;
import com.zhuxi.user.module.interfaces.vo.user.UserRegisterVO;
import com.zhuxi.user.module.interfaces.vo.user.UserViewVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理控制器
 * @author zhuxi
 */
@Slf4j
@Tag(name = "用户管理", description = "用户注册、登录、信息管理等相关接口")
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Operation(summary = "用户注册", description = "新用户注册接口，支持手机号注册")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "注册成功", 
                    content = @Content(schema = @Schema(implementation = UserRegisterVO.class))),
            @ApiResponse(responseCode = "500", description = "注册失败，可能原因：用户名已存在、手机号已存在、数据异常等")
    })
    @PostMapping("/register")
    public Result<UserRegisterVO> register(
            @Parameter(description = "用户注册信息", required = true)
            @RequestBody(required = true) @Valid UserRegisterDTO register) {
        User user = userService.register(register);
        if (user == null){
            return Result.fail(BusinessMessage.REGISTER_ERROR);
        }
        UserRegisterVO vo = UserConvert.COVERT.toUserRegisterVO(user);
        return Result.success(vo);
    }

    @Operation(summary = "用户登录", description = "用户登录接口，返回访问令牌和刷新令牌")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登录成功", 
                    content = @Content(schema = @Schema(implementation = UserLoginVO.class))),
            @ApiResponse(responseCode = "500", description = "登录失败，可能原因：账号或密码错误、用户被锁定、用户不存在等")
    })
    @PostMapping("/login")
    public Result<UserLoginVO> login(
            @Parameter(description = "用户登录信息", required = true)
            @RequestBody @Valid UserLoginDTO login){
        User user = userService.login(login);

        // 生成访问令牌
        Map<String,Object> data = new HashMap<>();
        data.put("userSn", user.getUserSn());
        data.put("role", user.getRole());
        TokenResult tokenResult = jwtUtils.generateUserToken(data);
        // 转换视图
        UserLoginVO vo = UserConvert.COVERT.toLoginVO(user, tokenResult.getAccessToken(), tokenResult.getAccessExpireTime());
        return Result.success(vo);
    }

    @Operation(summary = "用户登出", description = "用户登出接口，清除用户会话")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登出成功")
    })
    @PostMapping("/logout/{userSn}")
    public Result<String> logout(@PathVariable String userSn,@RequestHeader("Authorization") String token) {
        userService.logout(userSn, token);
        return Result.success();
    }

    @Operation(summary = "获取用户信息", description = "根据用户编号获取用户详细信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功", 
                    content = @Content(schema = @Schema(implementation = UserViewVO.class))),
            @ApiResponse(responseCode = "500", description = "获取失败，可能原因：用户不存在、数据异常等")
    })
    @GetMapping("/me/getInfo/{userSn}")
    @PermissionCheck(Role = Role.user,permission = "user:getInfo", enableDataOwnership = true)
    public Result<UserViewVO> getUserInfo(
            @Parameter(description = "用户编号", required = true)
            @PathVariable String userSn) {
        UserViewVO vo = userService.getUserInfo(userSn);
        return Result.success(vo);
    }

    @Operation(summary = "修改密码", description = "用户修改登录密码")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "密码修改成功"),
            @ApiResponse(responseCode = "500", description = "修改失败，可能原因：旧密码错误、修改密码失败等")
    })
    @PutMapping("/me/{userSn}/password")
    @PermissionCheck(Role = Role.user,permission = "user:changePassword", enableDataOwnership = true)
    public Result<String> updatePassword(
            @Parameter(description = "密码修改信息", required = true)
            @RequestBody @Valid UserUpdatePwDTO updatePw,
            @Parameter(description = "用户编号", required = true)
            @PathVariable String userSn,
            @RequestHeader("Authorization")
            String token
    ){
        userService.updatePassword(updatePw, userSn,token);
        return Result.success();
    }

    @Operation(summary = "更新用户信息", description = "更新用户基本信息，如昵称、邮箱、性别、头像等")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "500", description = "更新失败，可能原因：参数错误、修改失败等")
    })
    @PutMapping("/me/update/{userSn}")
    @PermissionCheck(Role = Role.user,permission = "user:updateInfo", enableDataOwnership = true)
    public Result<String> update(
            @Parameter(description = "用户信息更新数据", required = true)
            @RequestBody @Valid UserUpdateInfoDTO update,
            @Parameter(description = "用户编号", required = true)
            @PathVariable String userSn) {
        userService.updateInfo(update,userSn);
        return Result.success();
    }


    @Operation(summary = "刷新令牌", description = "刷新令牌接口，用于刷新访问令牌")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "刷新成功",
                    content = @Content(schema = @Schema(implementation = TokenResult.class))),
            @ApiResponse(responseCode = "500", description = "刷新失败，可能原因：刷新令牌错误、数据异常等")
    })
    @PostMapping("/refresh")
    public Result<TokenResult> refresh(
            @Parameter(description = "刷新令牌信息", required = true)
            @RequestBody @Valid RefreshDTO refresh) {
        RefreshToken token = userService.renewJwt(refresh);
        if (token == null){
            return Result.fail(AuthMessage.LOGIN_INVALID);
        }
        //构建访问令牌
        Map<String,Object> data = new HashMap<>();
        data.put("userSn", refresh.getUserSn());
        data.put("role", token.getRole());
        TokenResult tokenResult = jwtUtils.generateUserToken(data);

        return Result.success(tokenResult);
    }

}
