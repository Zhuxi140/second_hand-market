package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.Chat;
import com.zhuxi.server.helper.ChatMapperHelper;
import com.zhuxi.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    
    @Autowired
    private ChatMapperHelper chatMapperHelper;
    
    /**
     * 插入聊天记录
     */
    @Override
    public int insert(Chat chat) {
        return chatMapperHelper.insert(chat);
    }
    
    /**
     * 根据ID删除聊天记录
     */
    @Override
    public int deleteById(Long id) {
        return chatMapperHelper.deleteById(id);
    }
    
    /**
     * 更新聊天记录
     */
    @Override
    public int update(Chat chat) {
        return chatMapperHelper.update(chat);
    }
    
    /**
     * 根据ID查询聊天记录
     */
    @Override
    public Chat selectById(Long id) {
        return chatMapperHelper.selectById(id);
    }
    
    /**
     * 根据会话ID查询聊天记录列表
     */
    @Override
    public List<Chat> selectByConversationId(String conversationId) {
        return chatMapperHelper.selectByConversationId(conversationId);
    }
    
    /**
     * 根据发送者ID查询聊天记录列表
     */
    @Override
    public List<Chat> selectBySenderId(Long senderId) {
        return chatMapperHelper.selectBySenderId(senderId);
    }
    
    /**
     * 根据接收者ID查询聊天记录列表
     */
    @Override
    public List<Chat> selectByReceiverId(Long receiverId) {
        return chatMapperHelper.selectByReceiverId(receiverId);
    }
    
    /**
     * 查询所有聊天记录
     */
    @Override
    public List<Chat> selectAll() {
        return chatMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询聊天记录列表
     */
    @Override
    public List<Chat> selectByCondition(Chat chat) {
        return chatMapperHelper.selectByCondition(chat);
    }
}

