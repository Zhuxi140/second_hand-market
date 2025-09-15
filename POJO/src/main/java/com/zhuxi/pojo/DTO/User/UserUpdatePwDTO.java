package com.zhuxi.pojo.DTO.User;

import com.zhuxi.common.constant.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePwDTO {
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Size(min=5,max=20, message = "密码长度需在5-20字符之间")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "密码至少需要8位，包含字母和数字"
    )
    private String oldPassword;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Size(min=5,max=20, message = "密码长度需在5-20字符之间")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "密码至少需要8位，包含字母和数字"
    )
    private String newPassword;
}
