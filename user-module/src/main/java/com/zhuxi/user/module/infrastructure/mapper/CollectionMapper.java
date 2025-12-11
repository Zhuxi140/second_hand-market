package com.zhuxi.user.module.infrastructure.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 收藏MyBatis映射接口
 * 定义用户收藏关系的增删查SQL方法
 */
@Mapper
public interface CollectionMapper {

    /**
     * 根据用户编号查询用户ID
     */
    @Select("SELECT id FROM user WHERE userSn = #{sn}")
    Long selectUserIdBySn(String sn);

    /**
     * 根据商品编号查询商品ID
     */
    @Select("SELECT id FROM sh_product WHERE product_sn = #{sn}")
    Long selectProductIdBySn(String sn);

    /**
     * 插入收藏关系
     */
    @Insert("INSERT INTO user_collection(user_id, product_id) VALUES(#{userId}, #{productId})")
    int insert(Long userId, Long productId);

    /**
     * 删除收藏关系
     */
    @Delete("DELETE FROM user_collection WHERE user_id = #{userId} AND product_id = #{productId}")
    int delete(Long userId, Long productId);

    /**
     * 分页查询收藏商品简要信息
     */
    @Select("""
    SELECT p.product_sn AS productSn, p.title AS title, p.price AS price, p.condition AS `condition`, p.cover_image AS coverImage, p.created_at AS createdAt
    FROM user_collection c JOIN sh_product p ON c.product_id = p.id
    WHERE c.user_id = #{userId}
    ORDER BY c.id DESC
    LIMIT #{limit} OFFSET #{offset}
    """)
    List<Map<String,Object>> selectList(Long userId, int limit, int offset);
}
