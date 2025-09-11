package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.UserCollect;
import com.zhuxi.server.helper.UserCollectMapperHelper;
import com.zhuxi.server.service.UserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCollectServiceImpl implements UserCollectService {
    
    @Autowired
    private UserCollectMapperHelper userCollectMapperHelper;
    
    /**
     * 插入用户收藏
     */
    @Override
    public int insert(UserCollect userCollect) {
        return userCollectMapperHelper.insert(userCollect);
    }
    
    /**
     * 根据ID删除用户收藏
     */
    @Override
    public int deleteById(Long id) {
        return userCollectMapperHelper.deleteById(id);
    }
    
    /**
     * 根据用户ID和商品ID删除收藏
     */
    @Override
    public int deleteByUserIdAndProductId(Long userId, Long productId) {
        return userCollectMapperHelper.deleteByUserIdAndProductId(userId, productId);
    }
    
    /**
     * 更新用户收藏
     */
    @Override
    public int update(UserCollect userCollect) {
        return userCollectMapperHelper.update(userCollect);
    }
    
    /**
     * 根据ID查询用户收藏
     */
    @Override
    public UserCollect selectById(Long id) {
        return userCollectMapperHelper.selectById(id);
    }
    
    /**
     * 根据用户ID查询收藏列表
     */
    @Override
    public List<UserCollect> selectByUserId(Long userId) {
        return userCollectMapperHelper.selectByUserId(userId);
    }
    
    /**
     * 根据商品ID查询收藏列表
     */
    @Override
    public List<UserCollect> selectByProductId(Long productId) {
        return userCollectMapperHelper.selectByProductId(productId);
    }
    
    /**
     * 根据用户ID和商品ID查询收藏
     */
    @Override
    public UserCollect selectByUserIdAndProductId(Long userId, Long productId) {
        return userCollectMapperHelper.selectByUserIdAndProductId(userId, productId);
    }
    
    /**
     * 查询所有用户收藏
     */
    @Override
    public List<UserCollect> selectAll() {
        return userCollectMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询用户收藏列表
     */
    @Override
    public List<UserCollect> selectByCondition(UserCollect userCollect) {
        return userCollectMapperHelper.selectByCondition(userCollect);
    }
}
