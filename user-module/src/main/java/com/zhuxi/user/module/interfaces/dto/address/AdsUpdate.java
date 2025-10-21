package com.zhuxi.user.module.interfaces.dto.address;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
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

    @Schema(description = "收货人姓名", example = "张三", requiredMode = Schema.RequiredMode.REQUIRED)
    private String consignee;

    @Schema(description = "性别", example = "男", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sex;

    @Schema(description = "手机号", example = "13800138000", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;

    @Schema(description = "省份编码", example = "110000")
    private String provinceCode;

    @Schema(description = "省份名称", example = "北京市", requiredMode = Schema.RequiredMode.REQUIRED)
    private String provinceName;

    @Schema(description = "城市编码", example = "110100")
    private String cityCode;

    @Schema(description = "城市名称", example = "北京市", requiredMode =  Schema.RequiredMode.REQUIRED)
    private String cityName;

    @Schema(description = "区县编码", example = "110101")
    private String districtCode;

    @Schema(description = "区县名称", example = "东城区", requiredMode = Schema.RequiredMode.REQUIRED)
    private String districtName;

    @Schema(description = "详细地址", example = "某某街道123号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String detail;

    @Schema(description = "地址标签", example = "家")
    private String label;

    @Schema(description = "是否为默认地址", example = "1", allowableValues = {"0", "1"})
    private Integer isDefault;
}
