package com.zhuxi.userModule.interfaces.dto.user;

import com.zhuxi.common.constant.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuxi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Size(min=5,max=20, message = "用户名长度需在5-20字符之间")
    private String username;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "密码至少需要8位，包含字母和数字"
    )
    private String password;
}
