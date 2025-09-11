package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.Chat;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMapper {
    // 插入聊天记录
    @Insert("INSERT INTO chat (conversation_id, sender_id, receiver_id, content, is_read, sent_at) " +
            "VALUES (#{conversationId}, #{senderId}, #{receiverId}, #{content}, #{isRead}, #{sentAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Chat chat);
    
    // 根据ID删除聊天记录
    @Delete("DELETE FROM chat WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新聊天记录
    @Update("UPDATE chat SET conversation_id = #{conversationId}, sender_id = #{senderId}, receiver_id = #{receiverId}, " +
            "content = #{content}, is_read = #{isRead}, sent_at = #{sentAt} WHERE id = #{id}")
    int update(Chat chat);
    
    // 根据ID查询聊天记录
    @Select("SELECT * FROM chat WHERE id = #{id}")
    Chat selectById(@Param("id") Long id);
    
    // 根据会话ID查询聊天记录列表
    @Select("SELECT * FROM chat WHERE conversation_id = #{conversationId}")
    List<Chat> selectByConversationId(@Param("conversationId") String conversationId);
    
    // 根据发送者ID查询聊天记录列表
    @Select("SELECT * FROM chat WHERE sender_id = #{senderId}")
    List<Chat> selectBySenderId(@Param("senderId") Long senderId);
    
    // 根据接收者ID查询聊天记录列表
    @Select("SELECT * FROM chat WHERE receiver_id = #{receiverId}")
    List<Chat> selectByReceiverId(@Param("receiverId") Long receiverId);
    
    // 查询所有聊天记录
    @Select("SELECT * FROM chat")
    List<Chat> selectAll();
    
    // 根据条件查询聊天记录列表（复杂查询用XML）
    List<Chat> selectByCondition(Chat chat);
}
