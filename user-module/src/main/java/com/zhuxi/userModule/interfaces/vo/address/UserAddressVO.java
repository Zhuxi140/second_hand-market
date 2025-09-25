package com.zhuxi.userModule.interfaces.vo.address;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressVO {
    private String addressSn;
    private String consignee;
    private String sex;
    private String phone;
    private String provinceName;
    private String cityName;
    private String districtName;
    private String detail;
    private String label;
    private Integer isDefault;
}
