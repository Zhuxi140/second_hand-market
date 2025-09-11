package com.zhuxi.pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserAddress {
    private Long id;
    private Long userId;
    private String consignee;
    private String sex;
    private String phone;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String detail;
    private String label;
    private Integer isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
