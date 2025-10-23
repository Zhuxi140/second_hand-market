package com.zhuxi.user.module.domain.user.model;

import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.user.module.application.command.RegisterCommand;
import com.zhuxi.user.module.domain.user.enums.UserStatus;
import com.zhuxi.user.module.domain.user.valueObject.Email;
import com.zhuxi.user.module.domain.user.valueObject.Phone;
import com.zhuxi.user.module.domain.user.valueObject.RefreshToken;
import com.zhuxi.user.module.domain.user.valueObject.Username;
import com.zhuxi.user.module.interfaces.dto.user.UserRegisterDTO;
import com.zhuxi.user.module.interfaces.dto.user.UserUpdateInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.util.UUID;

/**
 * 用户领域模型
 * @author zhuxi
 */
@Schema(description = "用户实体")
@Getter
public class User {
    private Long id;
    private String userSn;
    private Username username;
    private String password;
    private String nickname;
    private Email email;
    private Phone phone;
    private String avatar;
    private UserStatus userStatus;
    private Role role;
    private String gender = null;
    private RefreshToken refreshToken;

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
        this.userStatus = UserStatus.getByCode(status);
        this.role = role;
                 }

    public User() {}

    // 注册
    public void register(RegisterCommand command){
        UserRegisterDTO register = command.getRegister();
        this.userSn = UUID.randomUUID().toString();
        this.username = new Username(register.getUsername());
        this.password = command.getHashPassword();
        this.nickname = command.getNickName();
        this.userStatus = UserStatus.ACTIVE;
        this.phone = new Phone(register.getPhone());
       /* this.avatar = register.get*/
        this.email = new Email(register.getEmail());
        /*this.gender = register.getGender();*/
        this.role = register.getRole();
        this.avatar  = command.getDefaultProperties().getDefaultUserAvatar();
    }

    // 登录
    public void login(RefreshToken token){
        this.refreshToken = token;
    }


    // 更新用户信息
    public void updateInfo(UserUpdateInfoDTO update){
        this.nickname = update.getNickname();
        this.email = new Email(update.getEmail());
        this.gender = update.getGender();
    }

    public void checkUserStatus(){
        if (this.userStatus == UserStatus.LOCKED){
            throw new BusinessException(BusinessMessage.USER_IS_LOCK);
        }
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
                ", status=" + userStatus +
                ", role=" + role +
                ", gender='" + gender + '\'' +
                '}';
    }

}
