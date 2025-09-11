package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.Admin;
import com.zhuxi.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    // 插入管理员
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody Admin admin) {
        int result = adminService.insert(admin);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除管理员
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = adminService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新管理员
    @PutMapping("/update")
    public Result<String> update(@RequestBody Admin admin) {
        int result = adminService.update(admin);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询管理员
    @GetMapping("/get/{id}")
    public Result<Admin> selectById(@PathVariable Long id) {
        Admin admin = adminService.selectById(id);
        return admin != null ? Result.success(admin) : Result.fail("管理员不存在");
    }
    
    // 查询所有管理员
    @GetMapping("/getAll")
    public Result<List<Admin>> selectAll() {
        List<Admin> admins = adminService.selectAll();
        return Result.success(admins);
    }
}
