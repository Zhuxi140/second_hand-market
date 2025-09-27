package com.zhuxi.userModule.interfaces.dto.address;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhuxi.common.constant.ValidationMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地址注册请求DTO
 * @author zhuxi
 */
@Schema(description = "地址注册请求参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsRegisterDTO {
    @Schema(description = "地址ID", hidden = true)
    @JsonIgnore
    private Long id;
    
    @Schema(description = "地址唯一标识", hidden = true)
    @JsonIgnore
    private String addressSn;
    
    @Schema(description = "用户ID", hidden = true)
    @JsonIgnore
    private Long userId;
    
    @Schema(description = "收货人姓名", example = "张三", required = true)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String consignee;
    
    @Schema(description = "性别", example = "男", required = true)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String sex;
    
    @Schema(description = "手机号", example = "13800138000", required = true)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;
    
    @Schema(description = "省份编码", example = "110000")
    private String provinceCode;
    
    @Schema(description = "省份名称", example = "北京市", required = true)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String provinceName;
    
    @Schema(description = "城市编码", example = "110100")
    private String cityCode;
    
    @Schema(description = "城市名称", example = "北京市", required = true)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String cityName;
    
    @Schema(description = "区县编码", example = "110101")
    private String districtCode;
    
    @Schema(description = "区县名称", example = "东城区", required = true)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String districtName;
    
    @Schema(description = "详细地址", example = "某某街道123号", required = true)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String detail;
    
    @Schema(description = "地址标签", example = "家")
    private String label;
    
    @Schema(description = "是否为默认地址", example = "1", allowableValues = {"0", "1"})
    private Integer isDefault;
}
