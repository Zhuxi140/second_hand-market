package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.UserAddress;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    // 插入地址
    @Insert("INSERT INTO user_address (user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label, is_default, created_at, updated_at) " +
            "VALUES (#{userId}, #{consignee}, #{sex}, #{phone}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}, #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserAddress userAddress);
    
    // 根据ID删除地址
    @Delete("DELETE FROM user_address WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新地址
    @Update("UPDATE user_address SET user_id = #{userId}, consignee = #{consignee}, sex = #{sex}, phone = #{phone}, " +
            "province_code = #{provinceCode}, province_name = #{provinceName}, city_code = #{cityCode}, city_name = #{cityName}, " +
            "district_code = #{districtCode}, district_name = #{districtName}, detail = #{detail}, label = #{label}, " +
            "is_default = #{isDefault}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(UserAddress userAddress);
    
    // 根据ID查询地址
    @Select("SELECT * FROM user_address WHERE id = #{id}")
    UserAddress selectById(@Param("id") Long id);
    
    // 根据用户ID查询地址列表
    @Select("SELECT * FROM user_address WHERE user_id = #{userId}")
    List<UserAddress> selectByUserId(@Param("userId") Long userId);
    
    // 根据用户ID查询默认地址
    @Select("SELECT * FROM user_address WHERE user_id = #{userId} AND is_default = 1")
    UserAddress selectDefaultByUserId(@Param("userId") Long userId);
    
    // 查询所有地址
    @Select("SELECT * FROM user_address")
    List<UserAddress> selectAll();
    
    // 根据条件查询地址列表（复杂查询用XML）
    List<UserAddress> selectByCondition(UserAddress userAddress);
}
