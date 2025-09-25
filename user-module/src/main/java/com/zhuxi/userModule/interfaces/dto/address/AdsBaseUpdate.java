package com.zhuxi.userModule.interfaces.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsBaseUpdate {
    private String consignee;
    private String sex;
    private String phone;
    private String label;
    private Integer isDefault;
}
