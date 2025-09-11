package com.zhuxi.server.service;

import com.zhuxi.pojo.entity.Chat;

import java.util.List;

public interface ChatService {
    
    /**
     * 插入聊天记录
     */
    int insert(Chat chat);
    
    /**
     * 根据ID删除聊天记录
     */
    int deleteById(Long id);
    
    /**
     * 更新聊天记录
     */
    int update(Chat chat);
    
    /**
     * 根据ID查询聊天记录
     */
    Chat selectById(Long id);
    
    /**
     * 根据会话ID查询聊天记录列表
     */
    List<Chat> selectByConversationId(String conversationId);
    
    /**
     * 根据发送者ID查询聊天记录列表
     */
    List<Chat> selectBySenderId(Long senderId);
    
    /**
     * 根据接收者ID查询聊天记录列表
     */
    List<Chat> selectByReceiverId(Long receiverId);
    
    /**
     * 查询所有聊天记录
     */
    List<Chat> selectAll();
    
    /**
     * 根据条件查询聊天记录列表
     */
    List<Chat> selectByCondition(Chat chat);
}
