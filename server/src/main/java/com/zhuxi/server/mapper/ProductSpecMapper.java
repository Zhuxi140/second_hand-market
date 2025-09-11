package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.ProductSpec;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductSpecMapper {
    // 插入商品规格
    @Insert("INSERT INTO product_spec (sku_sn, product_id, specifications, price, stock, sku_code, updated_at) " +
            "VALUES (#{skuSn}, #{productId}, #{specifications}, #{price}, #{stock}, #{skuCode}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProductSpec productSpec);
    
    // 根据ID删除商品规格
    @Delete("DELETE FROM product_spec WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新商品规格
    @Update("UPDATE product_spec SET sku_sn = #{skuSn}, product_id = #{productId}, specifications = #{specifications}, " +
            "price = #{price}, stock = #{stock}, sku_code = #{skuCode}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(ProductSpec productSpec);
    
    // 根据ID查询商品规格
    @Select("SELECT * FROM product_spec WHERE id = #{id}")
    ProductSpec selectById(@Param("id") Long id);
    
    // 根据商品ID查询商品规格列表
    @Select("SELECT * FROM product_spec WHERE product_id = #{productId}")
    List<ProductSpec> selectByProductId(@Param("productId") Long productId);
    
    // 根据SKU编号查询商品规格
    @Select("SELECT * FROM product_spec WHERE sku_sn = #{skuSn}")
    ProductSpec selectBySkuSn(@Param("skuSn") String skuSn);
    
    // 查询所有商品规格
    @Select("SELECT * FROM product_spec")
    List<ProductSpec> selectAll();
    
    // 根据条件查询商品规格列表（复杂查询用XML）
    List<ProductSpec> selectByCondition(ProductSpec productSpec);
}
