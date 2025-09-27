package com.zhuxi.userModule.domain.user.model;

import com.zhuxi.common.constant.BusinessMessage;
import com.zhuxi.common.exception.BusinessException;
import com.zhuxi.common.utils.BCryptUtils;
import com.zhuxi.userModule.domain.user.enums.Role;
import com.zhuxi.userModule.domain.user.valueObject.Email;
import com.zhuxi.userModule.domain.user.valueObject.Phone;
import com.zhuxi.userModule.domain.user.valueObject.Username;
import com.zhuxi.userModule.interfaces.dto.user.UserLoginDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdateInfoDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdatePwDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 用户领域模型
 * @author zhuxi
 */
@Schema(description = "用户实体")
@Getter
public class User {
    @Schema(description = "用户主键ID", example = "1")
    private Long id;
    
    @Schema(description = "用户对外唯一编号", example = "USR202401010001")
    private String userSn;
    
    @Schema(description = "用户名")
    private Username username;
    
    @Schema(description = "密码", hidden = true)
    private String password;
    
    @Schema(description = "用户昵称", example = "换换1234")
    private String nickname;
    
    @Schema(description = "邮箱地址")
    private Email email;
    
    @Schema(description = "手机号")
    private Phone phone;
    
    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;
    
    @Schema(description = "用户状态", example = "1")
    private Integer status;
    
    @Schema(description = "用户角色", example = "user")
    private Role role;
    
    @Schema(description = "性别", example = "男")
    private String gender = null;

    private User(Long id, String userSn, Username username, String password, String nickname,
                Email email, Phone phone, String gender, String avatar, Integer status, Role role) {
        this.id = id;
        this.userSn = userSn;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.avatar = avatar;
        this.status = status;
        this.role = role;
                 }

    public User() {}

    // 注册
    public void register(UserRegisterDTO  register, BCryptUtils bCryptUtils, ThreadLocalRandom random){
        //处理昵称
        String nickname = register.getNickname();
        if(nickname == null || nickname.isBlank()){
            nickname = "换换" + random.nextInt(10000);
        }
        this.userSn = UUID.randomUUID().toString();
        this.username = new Username(register.getUsername());
        this.password = bCryptUtils.hashCode(register.getPassword());
        this.nickname = nickname;
        this.status = 1;
        this.phone = new Phone(register.getPhone());
       /* this.avatar = register.get*/
        this.email = new Email(register.getEmail());
        /*this.gender = register.getGender();*/
        this.role = register.getRole();
        this.avatar  = "http://localhost:8080/Default_avatar/default.jpeg";
    }

    // 登录
    public boolean validateLogin(BCryptUtils bCryptUtils, User user, UserLoginDTO login, ThreadLocalRandom random){
        String hashPassword = user.getPassword();
        boolean outCome;
        try {
            outCome = bCryptUtils.checkPw(
                    hashPassword == null ? User.virtualHash.hash.get(random.nextInt(User.virtualHash.hash.size())) : login.getPassword(),
                    hashPassword
            );
        }catch (NullPointerException e){
            throw new BusinessException(BusinessMessage.USERNAME_OR_PASSWORD_ERROR);
        }
        return outCome;
    }

    // 修改密码
    public String changePsWd(BCryptUtils bCryptUtils, User user, UserUpdatePwDTO updatePw){
        boolean result = bCryptUtils.checkPw(updatePw.getOldPassword(), user.getPassword());
        if (!result){
            throw new BusinessException(BusinessMessage.USER_OLD_PASSWORD);
        }
        return bCryptUtils.hashCode(updatePw.getNewPassword());
    }

    // 更新用户信息
    public void updateInfo(UserUpdateInfoDTO update){
        this.nickname = update.getNickname();
        this.email = new Email(update.getEmail());
        this.gender = update.getGender();
        this.avatar = update.getAvatar();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userSn='" + userSn + '\'' +
                ", username=" + username +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email=" + email +
                ", phone=" + phone +
                ", avatar='" + avatar + '\'' +
                ", status=" + status +
                ", role=" + role +
                ", gender='" + gender + '\'' +
                '}';
    }

    private static class virtualHash{
        public static List<String> hash = List.of(
                "$2a$10$o0d2OaDqaXmnhJDw4934Guo17bVhKafy5PnwwkySttZJd1JiEBJbC",
                "$2a$10$zQSBxnXcy.Xh1kG1YFWwSO.VLm1DBeX1VB8yxjmHOKz5KUASWPWx.",
                "$2a$10$Yj9Jfl.sJXZiUjuWu94etePa9u.ZPYJBLEDPP8yNt4YHO/CIkyIxq",
                "$2a$10$jzjviFMYJA8COLaCg6Boieh0e1.Q.Y1AhPlomT8RC25sc1IuB6qQ.",
                "$2a$10$GG6r.eAdUNqxslQSAOHyTOZDk/qUCB3RLCUAzSriYfZkNOIqrASeC",
                "$2a$10$C8PO6p4iq8rnJhV0sYpNNO9WLj96hSp5tmTEPQfLtyf9hS4e/or3W"
        );
    }

}
