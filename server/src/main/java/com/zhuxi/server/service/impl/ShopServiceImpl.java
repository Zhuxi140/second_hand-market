package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.Shop;
import com.zhuxi.server.helper.ShopMapperHelper;
import com.zhuxi.server.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    
    @Autowired
    private ShopMapperHelper shopMapperHelper;
    
    /**
     * 插入店铺
     */
    @Override
    public int insert(Shop shop) {
        return shopMapperHelper.insert(shop);
    }
    
    /**
     * 根据ID删除店铺
     */
    @Override
    public int deleteById(Long id) {
        return shopMapperHelper.deleteById(id);
    }
    
    /**
     * 更新店铺
     */
    @Override
    public int update(Shop shop) {
        return shopMapperHelper.update(shop);
    }
    
    /**
     * 根据ID查询店铺
     */
    @Override
    public Shop selectById(Long id) {
        return shopMapperHelper.selectById(id);
    }
    
    /**
     * 根据用户ID查询店铺
     */
    @Override
    public Shop selectByUserId(Long userId) {
        return shopMapperHelper.selectByUserId(userId);
    }
    
    /**
     * 根据店铺名称查询店铺
     */
    @Override
    public Shop selectByShopName(String shopName) {
        return shopMapperHelper.selectByShopName(shopName);
    }
    
    /**
     * 查询所有店铺
     */
    @Override
    public List<Shop> selectAll() {
        return shopMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询店铺列表
     */
    @Override
    public List<Shop> selectByCondition(Shop shop) {
        return shopMapperHelper.selectByCondition(shop);
    }
}

