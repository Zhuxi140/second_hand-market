package com.zhuxi.userModule.interfaces.dto.address;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuxi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "地址更新请求参数")
public class AdsUpdate {
    @JsonIgnore
    private String addressSn;
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
}
