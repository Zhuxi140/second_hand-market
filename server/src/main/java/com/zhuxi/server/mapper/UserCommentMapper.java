package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.UserComment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserCommentMapper {
    // 插入用户评价
    @Insert("INSERT INTO user_comment (order_id, reviewer_id, reviewee_id, rating, comment, created_at, updated_at) " +
            "VALUES (#{orderId}, #{reviewerId}, #{revieweeId}, #{rating}, #{comment}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserComment userComment);
    
    // 根据ID删除用户评价
    @Delete("DELETE FROM user_comment WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新用户评价
    @Update("UPDATE user_comment SET order_id = #{orderId}, reviewer_id = #{reviewerId}, reviewee_id = #{revieweeId}, " +
            "rating = #{rating}, comment = #{comment}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(UserComment userComment);
    
    // 根据ID查询用户评价
    @Select("SELECT * FROM user_comment WHERE id = #{id}")
    UserComment selectById(@Param("id") Long id);
    
    // 根据订单ID查询用户评价
    @Select("SELECT * FROM user_comment WHERE order_id = #{orderId}")
    UserComment selectByOrderId(@Param("orderId") Long orderId);
    
    // 根据评价者ID查询评价列表
    @Select("SELECT * FROM user_comment WHERE reviewer_id = #{reviewerId}")
    List<UserComment> selectByReviewerId(@Param("reviewerId") Long reviewerId);
    
    // 根据被评价者ID查询评价列表
    @Select("SELECT * FROM user_comment WHERE reviewee_id = #{revieweeId}")
    List<UserComment> selectByRevieweeId(@Param("revieweeId") Long revieweeId);
    
    // 查询所有用户评价
    @Select("SELECT * FROM user_comment")
    List<UserComment> selectAll();
    
    // 根据条件查询用户评价列表（复杂查询用XML）
    List<UserComment> selectByCondition(UserComment userComment);
}
