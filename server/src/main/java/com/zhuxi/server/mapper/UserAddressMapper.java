package com.zhuxi.server.mapper;

import com.zhuxi.pojo.DTO.UserAddress.UserAdsLocationUpdate;
import com.zhuxi.pojo.DTO.UserAddress.UserAdsRegisterDTO;
import com.zhuxi.pojo.DTO.UserAddress.UserAdsBaseUpdate;
import com.zhuxi.pojo.DTO.UserAddress.UserAdsUpdate;
import com.zhuxi.pojo.VO.UserAddress.UserAddressVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    // 获取用户id
    @Select("SELECT id FROM user WHERE userSn = #{userSn}")
    Long getUserId(String userSn);

    // 获取用户地址总数
    @Select("SELECT COUNT(id) FROM user_address WHERE user_id = #{userId}")
    int getAddressCount(Long userId);


    // 插入地址
    @Insert("""
    INSERT INTO
    user_address (addressSn,user_id, consignee, sex, phone, province_code, province_name,
                  city_code, city_name, district_code, district_name, detail, label, is_default)
    VALUES (#{addressSn},#{userId}, #{consignee}, #{sex}, #{phone}, #{provinceCode}, #{provinceName},
            #{cityCode}, #{cityName}, #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})
""")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(UserAdsRegisterDTO address);


    // 根据ID查询地址号
    @Select("SELECT addressSn FROM user_address WHERE id = #{id}")
    String selectAddressSnById(@Param("id") Long id);

    // 检查地址是否默认
    @Select("SELECT id FROM user_address WHERE user_id = #{userId} AND is_default = 1")
    Long getAddressDefaultId(Long userId);


    // 取消默认地址
    @Update("UPDATE user_address SET is_default = 0 WHERE id = #{addressId}")
    int cancelDefault(@Param("addressId") Long addressId);
    
    // 根据Sn删除地址
    @Delete("DELETE FROM user_address WHERE addressSn = #{addressSn} AND user_id = #{userId}")
    int deleteById(String addressSn,Long userId);


    int updateAds(UserAdsUpdate  update, String addressSn);
    // 更新地址基础信息
    int updateBase(UserAdsBaseUpdate update, String addressSn);

    // 更新地址位置
    int updateLocation(UserAdsLocationUpdate update, String addressSn);

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
