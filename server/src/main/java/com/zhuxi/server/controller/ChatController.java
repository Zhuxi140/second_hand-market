package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.Chat;
import com.zhuxi.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    
    @Autowired
    private ChatService chatService;
    
    // 插入聊天记录
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody Chat chat) {
        int result = chatService.insert(chat);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除聊天记录
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = chatService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新聊天记录
    @PutMapping("/update")
    public Result<String> update(@RequestBody Chat chat) {
        int result = chatService.update(chat);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询聊天记录
    @GetMapping("/get/{id}")
    public Result<Chat> selectById(@PathVariable Long id) {
        Chat chat = chatService.selectById(id);
        return chat != null ? Result.success(chat) : Result.fail("聊天记录不存在");
    }
    
    // 查询所有聊天记录
    @GetMapping("/getAll")
    public Result<List<Chat>> selectAll() {
        List<Chat> chats = chatService.selectAll();
        return Result.success(chats);
    }
}
