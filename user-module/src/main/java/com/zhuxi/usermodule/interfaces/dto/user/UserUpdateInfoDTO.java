package com.zhuxi.usermodule.interfaces.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息更新请求DTO
 * @author zhuxi
 */
@Schema(description = "用户信息更新请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateInfoDTO {

    @Schema(description = "用户昵称", example = "新昵称")
    @Size(min = 2,max= 20, message = "昵称长度不能超过20字符或低于2个字符")
    private String nickname;
    
    @Schema(description = "邮箱地址", example = "newemail@example.com")
    @Pattern(
            regexp =  "^[A-Za-z0-9+_.-]+@[A-Za-z0-9_-]+\\.com$",
            message = "邮箱格式错误",
            flags = Pattern.Flag.CASE_INSENSITIVE
    )
    private String email;
    
    @Schema(description = "性别", example = "男", allowableValues = {"男", "女", ""})
    @Pattern(
            regexp = "男|女|^$",
            message = "性别只能为男、女、或不愿透露"
    )
    private String gender;
}
