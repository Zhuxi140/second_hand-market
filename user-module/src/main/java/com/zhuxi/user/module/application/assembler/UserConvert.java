package com.zhuxi.user.module.application.assembler;

import com.zhuxi.common.shared.constant.AuthMessage;
import com.zhuxi.common.shared.exception.TokenException;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.model.RefreshToken;
import com.zhuxi.user.module.domain.user.valueObject.Email;
import com.zhuxi.user.module.domain.user.valueObject.Phone;
import com.zhuxi.user.module.domain.user.valueObject.Username;
import com.zhuxi.user.module.interfaces.vo.user.UserLoginVO;
import com.zhuxi.user.module.interfaces.vo.user.UserRegisterVO;
import com.zhuxi.user.module.interfaces.vo.user.UserViewVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.ZoneId;

/**
 * @author zhuxi
 */
@Mapper
public interface UserConvert {

    UserConvert COVERT = Mappers.getMapper(UserConvert.class);

    UserRegisterVO toUserRegisterVO(User user);

    @Mapping(target = "username",source = "username",qualifiedByName = "usernameToString")
    @Mapping(target = "phone",source = "phone",qualifiedByName = "phoneToString")
    @Mapping(target = "email",source = "email",qualifiedByName = "emailToString")
    UserViewVO toUserViewVO(User user);

    @Mapping(target = "accessToken",source = "accessToken")
    @Mapping(target = "refreshToken",expression = "java(getRefreshToken(user))")
    @Mapping(target = "refreshExpireTime",expression = "java(getRefreshExpireTime(user))")
    @Mapping(target = "accessExpireTime",source = "accessExpireTime")
    UserLoginVO toLoginVO(User  user,String accessToken,Long accessExpireTime);

    @Named("usernameToString")
    default String convertUsername(Username username) {
        return username != null ? username.getAccountName() : null;
    }

    @Named("phoneToString")
    default String convertPhone(Phone phone) {
        return phone != null ? phone.getNumber() : null;
    }

    @Named("emailToString")
    default String convertEmail(Email email) {
        return email != null ? email.getAddress() : null;
    }

    default String getRefreshToken(User user){
        RefreshToken refreshToken = user.getRefreshToken();
        if (refreshToken == null){
            throw new TokenException(AuthMessage.OTHER_TOKEN_ERROR);
        }
        return refreshToken.getTokenHash();
    }

    default Long getRefreshExpireTime(User user){
        RefreshToken refreshToken = user.getRefreshToken();
        if (refreshToken == null){
            throw new TokenException(AuthMessage.OTHER_TOKEN_ERROR);
        }
        return refreshToken.getExpiresAt()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

}
