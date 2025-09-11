package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.User;
import com.zhuxi.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // 插入用户
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody User user) {
        int result = userService.insert(user);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除用户
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = userService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新用户
    @PutMapping("/update")
    public Result<String> update(@RequestBody User user) {
        int result = userService.update(user);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询用户
    @GetMapping("/get/{id}")
    public Result<User> selectById(@PathVariable Long id) {
        User user = userService.selectById(id);
        return user != null ? Result.success(user) : Result.fail("用户不存在");
    }
    
    // 根据用户名查询用户
    @GetMapping("/getByUsername/{username}")
    public Result<User> selectByUsername(@PathVariable String username) {
        User user = userService.selectByUsername(username);
        return user != null ? Result.success(user) : Result.fail("用户不存在");
    }
    
    // 查询所有用户
    @GetMapping("/getAll")
    public Result<List<User>> selectAll() {
        List<User> users = userService.selectAll();
        return Result.success(users);
    }
}
