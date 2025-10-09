package com.zhuxi.userModule.interfaces.controller;


import com.zhuxi.common.constant.BusinessMessage;
import com.zhuxi.common.result.Result;
import com.zhuxi.userModule.application.assembler.UserConvert;
import com.zhuxi.userModule.domain.user.model.User;
import com.zhuxi.userModule.domain.user.service.UserService;
import com.zhuxi.userModule.interfaces.dto.user.UserLoginDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdateInfoDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdatePwDTO;
import com.zhuxi.userModule.interfaces.vo.user.UserLoginVO;
import com.zhuxi.userModule.interfaces.vo.user.UserRegisterVO;
import com.zhuxi.userModule.interfaces.vo.user.UserViewVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 * @author zhuxi
 */
@Tag(name = "用户管理", description = "用户注册、登录、信息管理等相关接口")
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

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
        if (user == null){
            return Result.fail(BusinessMessage.USERNAME_OR_PASSWORD_ERROR);
        }
        UserLoginVO vo = UserConvert.COVERT.toLoginVO(user);
        return Result.success(vo);
    }

    @Operation(summary = "用户登出", description = "用户登出接口，清除用户会话")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登出成功")
    })
    @PostMapping("/logout")
    public Result<String> logout() {
        userService.logout();
        return Result.success();
    }

    @Operation(summary = "获取用户信息", description = "根据用户编号获取用户详细信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功", 
                    content = @Content(schema = @Schema(implementation = UserViewVO.class))),
            @ApiResponse(responseCode = "500", description = "获取失败，可能原因：用户不存在、数据异常等")
    })
    @GetMapping("/me/getInfo/{userSn}")
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
    public Result<String> updatePassword(
            @Parameter(description = "密码修改信息", required = true)
            @RequestBody @Valid UserUpdatePwDTO updatePw,
            @Parameter(description = "用户编号", required = true)
            @PathVariable String userSn
    ){
        userService.updatePassword(updatePw, userSn);
        return Result.success();
    }

    @Operation(summary = "更新用户信息", description = "更新用户基本信息，如昵称、邮箱、性别、头像等")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "500", description = "更新失败，可能原因：参数错误、修改失败等")
    })
    @PutMapping("/me/update/{userSn}")
    public Result<String> update(
            @Parameter(description = "用户信息更新数据", required = true)
            @RequestBody @Valid UserUpdateInfoDTO update,
            @Parameter(description = "用户编号", required = true)
            @PathVariable String userSn) {
        userService.updateInfo(update,userSn);
        return Result.success();
    }

}
