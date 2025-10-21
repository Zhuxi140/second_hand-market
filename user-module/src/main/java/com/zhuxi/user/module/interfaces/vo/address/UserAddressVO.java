package com.zhuxi.user.module.interfaces.vo.address;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户地址视图对象
 * @author zhuxi
 */
@Schema(description = "用户地址响应数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressVO {
    @Schema(description = "地址唯一标识", example = "ADDR202401010001")
    private String addressSn;
    
    @Schema(description = "收货人姓名", example = "张三")
    private String consignee;
    
    @Schema(description = "性别", example = "男")
    private String sex;
    
    @Schema(description = "手机号", example = "13800138000")
    private String phone;
    
    @Schema(description = "省份名称", example = "北京市")
    private String provinceName;
    
    @Schema(description = "城市名称", example = "北京市")
    private String cityName;
    
    @Schema(description = "区县名称", example = "东城区")
    private String districtName;
    
    @Schema(description = "详细地址", example = "某某街道123号")
    private String detail;
    
    @Schema(description = "地址标签", example = "家")
    private String label;
    
    @Schema(description = "是否为默认地址", example = "1", allowableValues = {"0", "1"})
    private Integer isDefault;
}
