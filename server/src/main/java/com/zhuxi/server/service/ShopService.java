package com.zhuxi.server.service;

import com.zhuxi.pojo.entity.Shop;

import java.util.List;

public interface ShopService {
    
    /**
     * 插入店铺
     */
    int insert(Shop shop);
    
    /**
     * 根据ID删除店铺
     */
    int deleteById(Long id);
    
    /**
     * 更新店铺
     */
    int update(Shop shop);
    
    /**
     * 根据ID查询店铺
     */
    Shop selectById(Long id);
    
    /**
     * 根据用户ID查询店铺
     */
    Shop selectByUserId(Long userId);
    
    /**
     * 根据店铺名称查询店铺
     */
    Shop selectByShopName(String shopName);
    
    /**
     * 查询所有店铺
     */
    List<Shop> selectAll();
    
    /**
     * 根据条件查询店铺列表
     */
    List<Shop> selectByCondition(Shop shop);
}
