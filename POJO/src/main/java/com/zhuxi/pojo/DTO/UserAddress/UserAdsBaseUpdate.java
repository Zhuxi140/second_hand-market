package com.zhuxi.pojo.DTO.UserAddress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAdsBaseUpdate {
    private String consignee;
    private String sex;
    private String phone;
    private String label;
    private Integer isDefault;
}
