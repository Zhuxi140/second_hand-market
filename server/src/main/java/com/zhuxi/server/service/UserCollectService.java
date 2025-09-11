package com.zhuxi.server.service;

import com.zhuxi.pojo.entity.UserCollect;

import java.util.List;

public interface UserCollectService {
    
    /**
     * 插入用户收藏
     */
    int insert(UserCollect userCollect);
    
    /**
     * 根据ID删除用户收藏
     */
    int deleteById(Long id);
    
    /**
     * 根据用户ID和商品ID删除收藏
     */
    int deleteByUserIdAndProductId(Long userId, Long productId);
    
    /**
     * 更新用户收藏
     */
    int update(UserCollect userCollect);
    
    /**
     * 根据ID查询用户收藏
     */
    UserCollect selectById(Long id);
    
    /**
     * 根据用户ID查询收藏列表
     */
    List<UserCollect> selectByUserId(Long userId);
    
    /**
     * 根据商品ID查询收藏列表
     */
    List<UserCollect> selectByProductId(Long productId);
    
    /**
     * 根据用户ID和商品ID查询收藏
     */
    UserCollect selectByUserIdAndProductId(Long userId, Long productId);
    
    /**
     * 查询所有用户收藏
     */
    List<UserCollect> selectAll();
    
    /**
     * 根据条件查询用户收藏列表
     */
    List<UserCollect> selectByCondition(UserCollect userCollect);
}
