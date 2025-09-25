package com.zhuxi.userModule.infrastructure.mapper;

import com.zhuxi.userModule.interfaces.dto.address.AdsBaseUpdate;
import com.zhuxi.userModule.interfaces.dto.address.AdsLocationUpdate;
import com.zhuxi.userModule.interfaces.dto.address.AdsRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.address.AdsUpdate;
import com.zhuxi.userModule.interfaces.vo.address.UserAddressVO;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @author zhuxi
 */

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
    int insert(AdsRegisterDTO address);


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


    int updateAds(AdsUpdate update, String addressSn);
    // 更新地址基础信息
    int updateBase(AdsBaseUpdate update, String addressSn);

    // 更新地址位置
    int updateLocation(AdsLocationUpdate update, String addressSn);

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
