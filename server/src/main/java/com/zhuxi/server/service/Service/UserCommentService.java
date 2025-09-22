package com.zhuxi.server.service.Service;

import com.zhuxi.pojo.entity.UserComment;

import java.util.List;

public interface UserCommentService {
    
    /**
     * 插入用户评价
     */
    int insert(UserComment userComment);
    
    /**
     * 根据ID删除用户评价
     */
    int deleteById(Long id);
    
    /**
     * 更新用户评价
     */
    int update(UserComment userComment);
    
    /**
     * 根据ID查询用户评价
     */
    UserComment selectById(Long id);
    
    /**
     * 根据订单ID查询用户评价
     */
    UserComment selectByOrderId(Long orderId);
    
    /**
     * 根据评价者ID查询评价列表
     */
    List<UserComment> selectByReviewerId(Long reviewerId);
    
    /**
     * 根据被评价者ID查询评价列表
     */
    List<UserComment> selectByRevieweeId(Long revieweeId);
    
    /**
     * 查询所有用户评价
     */
    List<UserComment> selectAll();
    
    /**
     * 根据条件查询用户评价列表
     */
    List<UserComment> selectByCondition(UserComment userComment);
}

