package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.ProductSort;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductSortMapper {
    // 插入商品分类
    @Insert("INSERT INTO product_sort (name, parent_id, icon_url, created_at, updated_at) " +
            "VALUES (#{name}, #{parentId}, #{iconUrl}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProductSort productSort);
    
    // 根据ID删除商品分类
    @Delete("DELETE FROM product_sort WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新商品分类
    @Update("UPDATE product_sort SET name = #{name}, parent_id = #{parentId}, icon_url = #{iconUrl}, " +
            "updated_at = #{updatedAt} WHERE id = #{id}")
    int update(ProductSort productSort);
    
    // 根据ID查询商品分类
    @Select("SELECT * FROM product_sort WHERE id = #{id}")
    ProductSort selectById(@Param("id") Long id);
    
    // 根据父ID查询商品分类列表
    @Select("SELECT * FROM product_sort WHERE parent_id = #{parentId}")
    List<ProductSort> selectByParentId(@Param("parentId") Long parentId);
    
    // 查询所有商品分类
    @Select("SELECT * FROM product_sort")
    List<ProductSort> selectAll();
    
    // 根据条件查询商品分类列表（复杂查询用XML）
    List<ProductSort> selectByCondition(ProductSort productSort);
}
