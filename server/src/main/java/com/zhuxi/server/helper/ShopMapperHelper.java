package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.Shop;
import com.zhuxi.server.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopMapperHelper {
    
    @Autowired
    private ShopMapper shopMapper;
    
    /**
     * 插入店铺
     */
    public int insert(Shop shop) {
        return shopMapper.insert(shop);
    }
    
    /**
     * 根据ID删除店铺
     */
    public int deleteById(Long id) {
        return shopMapper.deleteById(id);
    }
    
    /**
     * 更新店铺
     */
    public int update(Shop shop) {
        return shopMapper.update(shop);
    }
    
    /**
     * 根据ID查询店铺
     */
    public Shop selectById(Long id) {
        return shopMapper.selectById(id);
    }
    
    /**
     * 根据用户ID查询店铺
     */
    public Shop selectByUserId(Long userId) {
        return shopMapper.selectByUserId(userId);
    }
    
    /**
     * 根据店铺名称查询店铺
     */
    public Shop selectByShopName(String shopName) {
        return shopMapper.selectByShopName(shopName);
    }
    
    /**
     * 查询所有店铺
     */
    public List<Shop> selectAll() {
        return shopMapper.selectAll();
    }
    
    /**
     * 根据条件查询店铺列表
     */
    public List<Shop> selectByCondition(Shop shop) {
        return shopMapper.selectByCondition(shop);
    }
}
