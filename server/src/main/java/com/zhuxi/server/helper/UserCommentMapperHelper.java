package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.UserComment;
import com.zhuxi.server.mapper.UserCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCommentMapperHelper {
    
    @Autowired
    private UserCommentMapper userCommentMapper;
    
    /**
     * 插入用户评价
     */
    public int insert(UserComment userComment) {
        return userCommentMapper.insert(userComment);
    }
    
    /**
     * 根据ID删除用户评价
     */
    public int deleteById(Long id) {
        return userCommentMapper.deleteById(id);
    }
    
    /**
     * 更新用户评价
     */
    public int update(UserComment userComment) {
        return userCommentMapper.update(userComment);
    }
    
    /**
     * 根据ID查询用户评价
     */
    public UserComment selectById(Long id) {
        return userCommentMapper.selectById(id);
    }
    
    /**
     * 根据订单ID查询用户评价
     */
    public UserComment selectByOrderId(Long orderId) {
        return userCommentMapper.selectByOrderId(orderId);
    }
    
    /**
     * 根据评价者ID查询评价列表
     */
    public List<UserComment> selectByReviewerId(Long reviewerId) {
        return userCommentMapper.selectByReviewerId(reviewerId);
    }
    
    /**
     * 根据被评价者ID查询评价列表
     */
    public List<UserComment> selectByRevieweeId(Long revieweeId) {
        return userCommentMapper.selectByRevieweeId(revieweeId);
    }
    
    /**
     * 查询所有用户评价
     */
    public List<UserComment> selectAll() {
        return userCommentMapper.selectAll();
    }
    
    /**
     * 根据条件查询用户评价列表
     */
    public List<UserComment> selectByCondition(UserComment userComment) {
        return userCommentMapper.selectByCondition(userComment);
    }
}
