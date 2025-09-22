package com.zhuxi.server.mapper;

import com.zhuxi.pojo.DTO.Product.ProductSdDTO;
import com.zhuxi.pojo.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    // 发布二手商品
    @Insert("""
            INSERT INTO product (product_sn, seller_id, title, description, category_id, price, condition_id, status, location,is_draft)
            VALUES (#{productSn}, #{sellerId}, #{title}, #{description}, #{categoryId}, #{price}, #{conditionId}, #{status}, #{location},#{isDraft})
    """)
    int psdProduct(ProductSdDTO product,Integer isDraft);
    
    // 根据ID删除商品
    @Delete("DELETE FROM product WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新商品
    @Update("UPDATE product SET product_sn = #{productSn}, seller_id = #{sellerId}, shop_id = #{shopId}, " +
            "title = #{title}, description = #{description}, category_id = #{categoryId}, price = #{price}, " +
            "condition = #{condition}, status = #{status}, location = #{location}, view_count = #{viewCount}, " +
            "updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Product product);
    
    // 根据ID查询商品
    @Select("SELECT * FROM product WHERE id = #{id}")
    Product selectById(@Param("id") Long id);
    
    // 根据商品编号查询商品
    @Select("SELECT * FROM product WHERE product_sn = #{productSn}")
    Product selectByProductSn(@Param("productSn") String productSn);
    
    // 根据卖家ID查询商品列表
    @Select("SELECT * FROM product WHERE seller_id = #{sellerId}")
    List<Product> selectBySellerId(@Param("sellerId") Long sellerId);
    
    // 根据分类ID查询商品列表
    @Select("SELECT * FROM product WHERE category_id = #{categoryId}")
    List<Product> selectByCategoryId(@Param("categoryId") Long categoryId);
    
    // 查询所有商品
    @Select("SELECT * FROM product")
    List<Product> selectAll();
    
    // 根据条件查询商品列表（复杂查询用XML）
    List<Product> selectByCondition(Product product);
}
