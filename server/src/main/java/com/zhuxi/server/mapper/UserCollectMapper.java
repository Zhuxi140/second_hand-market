package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.UserCollect;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserCollectMapper {
    // 插入用户收藏
    @Insert("INSERT INTO user_collect (user_id, product_id, created_at, updated_at) " +
            "VALUES (#{userId}, #{productId}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserCollect userCollect);
    
    // 根据ID删除用户收藏
    @Delete("DELETE FROM user_collect WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 根据用户ID和商品ID删除收藏
    @Delete("DELETE FROM user_collect WHERE user_id = #{userId} AND product_id = #{productId}")
    int deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
    
    // 更新用户收藏
    @Update("UPDATE user_collect SET user_id = #{userId}, product_id = #{productId}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(UserCollect userCollect);
    
    // 根据ID查询用户收藏
    @Select("SELECT * FROM user_collect WHERE id = #{id}")
    UserCollect selectById(@Param("id") Long id);
    
    // 根据用户ID查询收藏列表
    @Select("SELECT * FROM user_collect WHERE user_id = #{userId}")
    List<UserCollect> selectByUserId(@Param("userId") Long userId);
    
    // 根据商品ID查询收藏列表
    @Select("SELECT * FROM user_collect WHERE product_id = #{productId}")
    List<UserCollect> selectByProductId(@Param("productId") Long productId);
    
    // 根据用户ID和商品ID查询收藏
    @Select("SELECT * FROM user_collect WHERE user_id = #{userId} AND product_id = #{productId}")
    UserCollect selectByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
    
    // 查询所有用户收藏
    @Select("SELECT * FROM user_collect")
    List<UserCollect> selectAll();
    
    // 根据条件查询用户收藏列表（复杂查询用XML）
    List<UserCollect> selectByCondition(UserCollect userCollect);
}
