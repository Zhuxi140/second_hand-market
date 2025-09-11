package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.Shop;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopMapper {
    // 插入店铺
    @Insert("INSERT INTO shop (user_id, shop_name, description, logo_url, status, created_at, updated_at) " +
            "VALUES (#{userId}, #{shopName}, #{description}, #{logoUrl}, #{status}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Shop shop);
    
    // 根据ID删除店铺
    @Delete("DELETE FROM shop WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新店铺
    @Update("UPDATE shop SET user_id = #{userId}, shop_name = #{shopName}, description = #{description}, " +
            "logo_url = #{logoUrl}, status = #{status}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Shop shop);
    
    // 根据ID查询店铺
    @Select("SELECT * FROM shop WHERE id = #{id}")
    Shop selectById(@Param("id") Long id);
    
    // 根据用户ID查询店铺
    @Select("SELECT * FROM shop WHERE user_id = #{userId}")
    Shop selectByUserId(@Param("userId") Long userId);
    
    // 根据店铺名称查询店铺
    @Select("SELECT * FROM shop WHERE shop_name = #{shopName}")
    Shop selectByShopName(@Param("shopName") String shopName);
    
    // 查询所有店铺
    @Select("SELECT * FROM shop")
    List<Shop> selectAll();
    
    // 根据条件查询店铺列表（复杂查询用XML）
    List<Shop> selectByCondition(Shop shop);
}
