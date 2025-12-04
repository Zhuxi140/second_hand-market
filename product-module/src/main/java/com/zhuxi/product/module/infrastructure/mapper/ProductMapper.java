package com.zhuxi.product.module.infrastructure.mapper;

import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.model.ProductStatic;
import com.zhuxi.product.module.domain.objectValue.HostScore;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhuxi
 */
@Mapper
public interface ProductMapper {

    @Select("SELECT id FROM product WHERE product_sn = #{productSn}")
    Long getProductIdBySn(String productSn);

    @Select("SELECT id FROM user WHERE userSn = #{userSn} ")
    Long getUserIdBySn(String userSn);

    @Select("SELECT nickname,avatar FROM user WHERE userSn = #{userSn}")
    List<Object> getSellerInfo(String userSn);

    @Select("SELECT id,name,parent_id,icon_url FROM product_sort LIMIT #{limit} OFFSET #{offset}")
    List<CategoryVO> getCategoryList(int limit,int offset);

    int insert(Product product);

    int update(Product product);

    @Select("SELECT id,name FROM product_condition WHERE type != 0")
    List<ConditionSHVO> getShConditions();

    List<ShProductVO> getShProductListDesc(ShProductParam param);

    List<ShProductVO> getShProductListAsc(ShProductParam param);

    ProductDetailVO getShProductDetail(Long productId);

    @Select("""
    SELECT
    sku_id,
    image_url,
    image_type
    FROM product_static ps JOIN product p ON ps.product_id = p.id
    WHERE p.id = #{productId}
    """)
    List<ProductImage> getProductImages(Long productId);

    @Delete("DELETE FROM product WHERE id = #{productId}")
    Integer delProduct(Long productId);

    @Select("""
    SELECT
    p.product_sn,p.title,p.price,ps.image_url,p.view_count,p.is_draft,p.created_at
    FROM product p JOIN product_static ps ON p.id = ps.product_id
     WHERE p.seller_id = #{userId} AND ps.image_type = 1
    """)
    List<MeShProductVO> getMeShProductList(Long userId);

    @Select("SELECT name FROM product_condition WHERE id = #{conditionId}")
    String gerConditionNameById(Integer conditionId);

    @Select("SELECT name FROM product_sort WHERE id = #{categoryId}")
    String getCategoryNameById(Long categoryId);

    @Select("""
    SELECT
    ps.id,ps.product_id,ps.sku_id,ps.image_url,ps.image_type,ps.sort_order
    FROM product_static ps JOIN product p ON ps.product_id = p.id
    WHERE p.id = #{productId}
    ORDER BY ps.sort_order ASC
    """)
    List<ProductStatic> getProductStatics(Long productId);

    @Select("""
    SELECT id,product_sn,seller_id,shop_id,title,description,
           category_id,price,condition_id,status,location,view_count,is_draft,created_at as create_time
    FROM product
    WHERE id = #{productId}
    """)
    Product getProductForCache(Long productId);

    @Select("SELECT userSn FROM user WHERE id = #{userId} ")
    String getUserSn(Long userId);

    @Select("SELECT host_score FROM product WHERE id = #{productId} ")
    HostScore getHostScore(Long productId);

}
