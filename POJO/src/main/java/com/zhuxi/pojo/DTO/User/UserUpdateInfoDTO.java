package com.zhuxi.pojo.DTO.User;


import com.zhuxi.common.constant.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateInfoDTO {

    @Size(min = 2,max= 20, message = "昵称长度不能超过20字符或低于2个字符")
    private String nickname;
    @Pattern(
            regexp =  "^[A-Za-z0-9+_.-]+@[A-Za-z0-9_-]+\\.com$",
            message = "邮箱格式错误",
            flags = Pattern.Flag.CASE_INSENSITIVE
    )
    private String email;
    @Pattern(
            regexp = "男|女|^$",
            message = "性别只能为男、女、或不愿透露"
    )
    private String gender;
    private String avatar;
}
