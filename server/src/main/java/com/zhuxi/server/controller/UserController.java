package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.DTO.User.UserLoginDTO;
import com.zhuxi.pojo.DTO.User.UserRegisterDTO;
import com.zhuxi.pojo.DTO.User.UserUpdatePwDTO;
import com.zhuxi.pojo.VO.User.UserLoginVO;
import com.zhuxi.pojo.VO.User.UserRegisterVO;
import com.zhuxi.pojo.VO.User.UserViewVO;
import com.zhuxi.pojo.entity.User;
import com.zhuxi.server.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
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
    @GetMapping("/getInfo/{userSn}")
    public Result<UserViewVO> getUserInfo(@PathVariable String userSn) {
        return userService.getUserInfo(userSn);
    }

    // 更新密码
    @PutMapping("/{userSn}/password")
    public Result<String> updatePassword(
            @RequestBody @Valid UserUpdatePwDTO updatePw,
            @PathVariable String userSn
    ){
        return userService.updatePassword(updatePw, userSn);
    }

    
    // 更新用户
    @PutMapping("/update")
    public Result<String> update(@RequestBody User user) {
        int result = userService.update(user);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }

}
