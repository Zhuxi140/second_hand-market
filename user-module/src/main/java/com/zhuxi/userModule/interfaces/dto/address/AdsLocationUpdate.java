package com.zhuxi.userModule.interfaces.dto.address;


import com.zhuxi.common.constant.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsLocationUpdate {
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String provinceCode;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String provinceName;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String cityCode;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String cityName;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String districtCode;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String districtName;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String detail;
}
