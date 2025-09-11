package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.UserCollect;
import com.zhuxi.server.mapper.UserCollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCollectMapperHelper {
    
    @Autowired
    private UserCollectMapper userCollectMapper;
    
    /**
     * 插入用户收藏
     */
    public int insert(UserCollect userCollect) {
        return userCollectMapper.insert(userCollect);
    }
    
    /**
     * 根据ID删除用户收藏
     */
    public int deleteById(Long id) {
        return userCollectMapper.deleteById(id);
    }
    
    /**
     * 根据用户ID和商品ID删除收藏
     */
    public int deleteByUserIdAndProductId(Long userId, Long productId) {
        return userCollectMapper.deleteByUserIdAndProductId(userId, productId);
    }
    
    /**
     * 更新用户收藏
     */
    public int update(UserCollect userCollect) {
        return userCollectMapper.update(userCollect);
    }
    
    /**
     * 根据ID查询用户收藏
     */
    public UserCollect selectById(Long id) {
        return userCollectMapper.selectById(id);
    }
    
    /**
     * 根据用户ID查询收藏列表
     */
    public List<UserCollect> selectByUserId(Long userId) {
        return userCollectMapper.selectByUserId(userId);
    }
    
    /**
     * 根据商品ID查询收藏列表
     */
    public List<UserCollect> selectByProductId(Long productId) {
        return userCollectMapper.selectByProductId(productId);
    }
    
    /**
     * 根据用户ID和商品ID查询收藏
     */
    public UserCollect selectByUserIdAndProductId(Long userId, Long productId) {
        return userCollectMapper.selectByUserIdAndProductId(userId, productId);
    }
    
    /**
     * 查询所有用户收藏
     */
    public List<UserCollect> selectAll() {
        return userCollectMapper.selectAll();
    }
    
    /**
     * 根据条件查询用户收藏列表
     */
    public List<UserCollect> selectByCondition(UserCollect userCollect) {
        return userCollectMapper.selectByCondition(userCollect);
    }
}
