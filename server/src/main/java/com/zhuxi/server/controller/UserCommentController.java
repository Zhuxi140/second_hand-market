package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.UserComment;
import com.zhuxi.server.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userComment")
@CrossOrigin(origins = "*")
public class UserCommentController {
    
    @Autowired
    private UserCommentService userCommentService;
    
    // 插入用户评价
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody UserComment userComment) {
        int result = userCommentService.insert(userComment);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除用户评价
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = userCommentService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新用户评价
    @PutMapping("/update")
    public Result<String> update(@RequestBody UserComment userComment) {
        int result = userCommentService.update(userComment);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询用户评价
    @GetMapping("/get/{id}")
    public Result<UserComment> selectById(@PathVariable Long id) {
        UserComment userComment = userCommentService.selectById(id);
        return userComment != null ? Result.success(userComment) : Result.fail("用户评价不存在");
    }
    
    // 查询所有用户评价
    @GetMapping("/getAll")
    public Result<List<UserComment>> selectAll() {
        List<UserComment> userComments = userCommentService.selectAll();
        return Result.success(userComments);
    }
}

