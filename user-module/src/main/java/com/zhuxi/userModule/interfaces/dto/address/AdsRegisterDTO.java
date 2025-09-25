package com.zhuxi.userModule.interfaces.dto.address;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhuxi.common.constant.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsRegisterDTO {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String addressSn;
    @JsonIgnore
    private Long userId;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String consignee;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String sex;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;
    private String provinceCode;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String provinceName;
    private String cityCode;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String cityName;
    private String districtCode;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String districtName;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String detail;
    private String label;
    private Integer isDefault;
}
