package com.zhuxi.userModule.interfaces.controller;


import com.zhuxi.common.result.Result;
import com.zhuxi.userModule.domain.user.service.UserService;
import com.zhuxi.userModule.interfaces.dto.user.UserLoginDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdateInfoDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdatePwDTO;
import com.zhuxi.userModule.interfaces.vo.user.UserLoginVO;
import com.zhuxi.userModule.interfaces.vo.user.UserRegisterVO;
import com.zhuxi.userModule.interfaces.vo.user.UserViewVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuxi
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    // 插入用户
    @PostMapping("/register")
    public Result<UserRegisterVO> register(@RequestBody(required = true) @Valid UserRegisterDTO user) {
        return userService.register(user);
    }

    // 登录
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody @Valid UserLoginDTO login){
        return userService.login(login);
    }

    // 登出
    @PostMapping("/logout")
    public Result<String> logout() {
        return userService.logout();
    }

    // 获取用户信息
    @GetMapping("/me/getInfo/{userSn}")
    public Result<UserViewVO> getUserInfo(@PathVariable String userSn) {
        return userService.getUserInfo(userSn);
    }

    // 更新密码
    @PutMapping("/me/{userSn}/password")
    public Result<String> updatePassword(
            @RequestBody @Valid UserUpdatePwDTO updatePw,
            @PathVariable String userSn
    ){
        return userService.updatePassword(updatePw, userSn);
    }

    
    // 更新用户
    @PutMapping("/me/update/{userSn}")
    public Result<String> update(@RequestBody @Valid UserUpdateInfoDTO user, @PathVariable String userSn) {
        return userService.updateInfo(user,userSn);
    }

}
