package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.UserCollect;
import com.zhuxi.server.service.UserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userCollect")
@CrossOrigin(origins = "*")
public class UserCollectController {
    
    @Autowired
    private UserCollectService userCollectService;
    
    // 插入用户收藏
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody UserCollect userCollect) {
        int result = userCollectService.insert(userCollect);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除用户收藏
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = userCollectService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新用户收藏
    @PutMapping("/update")
    public Result<String> update(@RequestBody UserCollect userCollect) {
        int result = userCollectService.update(userCollect);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询用户收藏
    @GetMapping("/get/{id}")
    public Result<UserCollect> selectById(@PathVariable Long id) {
        UserCollect userCollect = userCollectService.selectById(id);
        return userCollect != null ? Result.success(userCollect) : Result.fail("用户收藏不存在");
    }
    
    // 查询所有用户收藏
    @GetMapping("/getAll")
    public Result<List<UserCollect>> selectAll() {
        List<UserCollect> userCollects = userCollectService.selectAll();
        return Result.success(userCollects);
    }
}

