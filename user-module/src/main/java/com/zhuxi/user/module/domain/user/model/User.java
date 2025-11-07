package com.zhuxi.user.module.domain.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.common.shared.utils.JackSonUtils;
import com.zhuxi.user.module.application.command.RegisterCommand;
import com.zhuxi.user.module.domain.user.enums.Gender;
import com.zhuxi.user.module.domain.user.enums.UserStatus;
import com.zhuxi.user.module.domain.user.valueObject.Email;
import com.zhuxi.user.module.domain.user.valueObject.Phone;
import com.zhuxi.user.module.domain.user.valueObject.Username;
import com.zhuxi.user.module.interfaces.dto.user.UserRegisterDTO;
import com.zhuxi.user.module.interfaces.dto.user.UserUpdateInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
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
    private Gender gender = Gender.UNKNOWN;
    private RefreshToken refreshToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(Long id, String userSn, Username username, String password, String nickname,
                Email email, Phone phone, Gender gender, String avatar, UserStatus status, Role role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userSn = userSn;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.avatar = avatar;
        this.userStatus = status;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User() {}

    // 注册
    public void register(RegisterCommand command){
        UserRegisterDTO register = command.getRegister();
        String uu = UUID.randomUUID().toString();
        this.userSn = uu.replace("-", "");
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

    /**
     * 登录
     * @param token 刷新令牌
     */
    public void login(RefreshToken token){
        this.refreshToken = token;
    }


    /**
     * 修改用户信息
     * @param update 修改用户信息DTO
     */
    public void updateInfo(UserUpdateInfoDTO update){
        this.nickname = update.getNickname();
        this.email = new Email(update.getEmail());
        this.gender = Gender.getByCode(update.getGender());
    }

    /**
     * 构建缓存用户信息
     * @param map 缓存用户信息
     * @return 缓存用户信息
     */
    public User buildCacheUserInfo(Map<String,Object> map){
        Long id = map.containsKey("id") ? this.id = Long.valueOf((Integer) map.get("id")) : null;
        this.userSn = map.containsKey("userSn") ?  (String) map.get("userSn") : null;
        this.username = map.containsKey("username") ?  JackSonUtils.convert(map.get("username"), Username.class) : null;
        this.password = map.containsKey("password") ?  (String) map.get("password") : null;
        this.nickname = map.containsKey("nickname") ? (String) map.get("nickname") : null;
        this.email = map.containsKey("email") ? JackSonUtils.convert(map.get("email"),Email.class) : null;
        this.phone = map.containsKey("phone") ? JackSonUtils.convert(map.get("phone"),Phone.class) : null;
        this.gender = map.containsKey("gender") ?  Gender.getByCode((Integer)map.get("gender")) : null;
        this.role = map.containsKey("role") ?  Role.getRoleById((Integer) map.get("role")) : null;
        this.avatar = map.containsKey("avatar") ? (String) map.get("avatar") : null;
        this.userStatus = map.containsKey("status") ?  UserStatus.getByCode((Integer) map.get("status")) : null;
        this.createdAt = map.containsKey("createdAt") ? LocalDateTime.parse(map.get("createdAt").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))  : null;
        return new User(id, userSn, username, password, nickname, email, phone, gender, avatar, userStatus, role,createdAt,null );
    }

    /**
     * 检查用户状态
     */
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
