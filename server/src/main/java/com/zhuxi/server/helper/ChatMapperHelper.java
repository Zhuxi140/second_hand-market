package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.Chat;
import com.zhuxi.server.mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatMapperHelper {
    
    @Autowired
    private ChatMapper chatMapper;
    
    /**
     * 插入聊天记录
     */
    public int insert(Chat chat) {
        return chatMapper.insert(chat);
    }
    
    /**
     * 根据ID删除聊天记录
     */
    public int deleteById(Long id) {
        return chatMapper.deleteById(id);
    }
    
    /**
     * 更新聊天记录
     */
    public int update(Chat chat) {
        return chatMapper.update(chat);
    }
    
    /**
     * 根据ID查询聊天记录
     */
    public Chat selectById(Long id) {
        return chatMapper.selectById(id);
    }
    
    /**
     * 根据会话ID查询聊天记录列表
     */
    public List<Chat> selectByConversationId(String conversationId) {
        return chatMapper.selectByConversationId(conversationId);
    }
    
    /**
     * 根据发送者ID查询聊天记录列表
     */
    public List<Chat> selectBySenderId(Long senderId) {
        return chatMapper.selectBySenderId(senderId);
    }
    
    /**
     * 根据接收者ID查询聊天记录列表
     */
    public List<Chat> selectByReceiverId(Long receiverId) {
        return chatMapper.selectByReceiverId(receiverId);
    }
    
    /**
     * 查询所有聊天记录
     */
    public List<Chat> selectAll() {
        return chatMapper.selectAll();
    }
    
    /**
     * 根据条件查询聊天记录列表
     */
    public List<Chat> selectByCondition(Chat chat) {
        return chatMapper.selectByCondition(chat);
    }
}
