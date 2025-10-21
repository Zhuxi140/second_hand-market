package com.zhuxi.user.module.infrastructure.mapper;

import com.zhuxi.user.module.domain.address.model.UserAddress;
import com.zhuxi.user.module.interfaces.vo.address.UserAddressVO;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @author zhuxi
 */

@Mapper
public interface UserAddressMapper {
    // 获取用户id
    @Select("SELECT id AS userId FROM user WHERE userSn = #{userSn}")
    UserAddress getUserId(String userSn);

    // 获取地址id
    @Select("SELECT id,is_default FROM user_address WHERE addressSn = #{addressSn}")
    UserAddress getAdIdOrDefaultBySn(String addressSn);

    // 获取用户地址总数
    @Select("SELECT COUNT(id) FROM user_address WHERE user_id = #{userId}")
    int getAddressCount(Long userId);


    // 通用插入

    int insert(UserAddress address);

    //通用更新
    int update(UserAddress address);


    // 根据ID查询地址号
    @Select("SELECT addressSn FROM user_address WHERE id = #{id}")
    String selectAddressSnById(@Param("id") Long id);

    // 检查地址是否默认
    @Select("SELECT id FROM user_address WHERE user_id = #{userId} AND is_default = 1")
    Long getDefaultId(Long userId);


    // 取消默认地址
    @Update("UPDATE user_address SET is_default = 0 WHERE id = #{addressId}")
    int cancelDefault(@Param("addressId") Long addressId);
    
    // 根据Sn删除地址
    @Delete("DELETE FROM user_address WHERE addressSn = #{addressSn}")
    int deleteBySn(String addressSn);


    @Select("""
    SELECT
        addressSn,
        consignee,
        sex,
        phone,
        province_name,
        city_name,
        district_name,
        detail,
        label,
        is_default
    FROM user_address WHERE user_id = #{userId}
    """)
    List<UserAddressVO> gerListAddress(Long userId);


}
