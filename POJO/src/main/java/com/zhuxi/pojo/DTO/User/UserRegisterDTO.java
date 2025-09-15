package com.zhuxi.pojo.DTO.User;

import com.zhuxi.common.Enum.Role;
import com.zhuxi.common.constant.ValidationMessage;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private String userSn;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Size(min=5,max=20, message = "用户名长度需在5-20字符之间")
    private String username;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
    message = "密码至少需要8位，包含字母和数字"
    )
    private String password;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;
    private Role role = Role.user;
    private String email;
    @Size(min =2,max=20, message = "昵称长度不能超过20字符或低于2个字符")
    private String nickname;


}
