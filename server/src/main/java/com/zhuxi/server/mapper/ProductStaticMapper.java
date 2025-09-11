package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.ProductStatic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductStaticMapper {
    // 插入商品图片
    @Insert("INSERT INTO product_static (product_id, sku_id, image_url, image_type, sort_order, created_at, updated_at) " +
            "VALUES (#{productId}, #{skuId}, #{imageUrl}, #{imageType}, #{sortOrder}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProductStatic productStatic);
    
    // 根据ID删除商品图片
    @Delete("DELETE FROM product_static WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新商品图片
    @Update("UPDATE product_static SET product_id = #{productId}, sku_id = #{skuId}, image_url = #{imageUrl}, " +
            "image_type = #{imageType}, sort_order = #{sortOrder}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(ProductStatic productStatic);
    
    // 根据ID查询商品图片
    @Select("SELECT * FROM product_static WHERE id = #{id}")
    ProductStatic selectById(@Param("id") Long id);
    
    // 根据商品ID查询商品图片列表
    @Select("SELECT * FROM product_static WHERE product_id = #{productId}")
    List<ProductStatic> selectByProductId(@Param("productId") Long productId);
    
    // 根据商品ID和图片类型查询商品图片列表
    @Select("SELECT * FROM product_static WHERE product_id = #{productId} AND image_type = #{imageType}")
    List<ProductStatic> selectByProductIdAndType(@Param("productId") Long productId, @Param("imageType") Integer imageType);
    
    // 查询所有商品图片
    @Select("SELECT * FROM product_static")
    List<ProductStatic> selectAll();
    
    // 根据条件查询商品图片列表（复杂查询用XML）
    List<ProductStatic> selectByCondition(ProductStatic productStatic);
}
