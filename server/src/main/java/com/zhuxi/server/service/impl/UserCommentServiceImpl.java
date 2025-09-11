package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.UserComment;
import com.zhuxi.server.helper.UserCommentMapperHelper;
import com.zhuxi.server.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCommentServiceImpl implements UserCommentService {
    
    @Autowired
    private UserCommentMapperHelper userCommentMapperHelper;
    
    /**
     * 插入用户评价
     */
    @Override
    public int insert(UserComment userComment) {
        return userCommentMapperHelper.insert(userComment);
    }
    
    /**
     * 根据ID删除用户评价
     */
    @Override
    public int deleteById(Long id) {
        return userCommentMapperHelper.deleteById(id);
    }
    
    /**
     * 更新用户评价
     */
    @Override
    public int update(UserComment userComment) {
        return userCommentMapperHelper.update(userComment);
    }
    
    /**
     * 根据ID查询用户评价
     */
    @Override
    public UserComment selectById(Long id) {
        return userCommentMapperHelper.selectById(id);
    }
    
    /**
     * 根据订单ID查询用户评价
     */
    @Override
    public UserComment selectByOrderId(Long orderId) {
        return userCommentMapperHelper.selectByOrderId(orderId);
    }
    
    /**
     * 根据评价者ID查询评价列表
     */
    @Override
    public List<UserComment> selectByReviewerId(Long reviewerId) {
        return userCommentMapperHelper.selectByReviewerId(reviewerId);
    }
    
    /**
     * 根据被评价者ID查询评价列表
     */
    @Override
    public List<UserComment> selectByRevieweeId(Long revieweeId) {
        return userCommentMapperHelper.selectByRevieweeId(revieweeId);
    }
    
    /**
     * 查询所有用户评价
     */
    @Override
    public List<UserComment> selectAll() {
        return userCommentMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询用户评价列表
     */
    @Override
    public List<UserComment> selectByCondition(UserComment userComment) {
        return userCommentMapperHelper.selectByCondition(userComment);
    }
}
