package com.zhuxi.user.module.application.assembler;

import com.zhuxi.common.shared.constant.AuthMessage;
import com.zhuxi.common.shared.exception.TokenException;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.valueObject.RefreshToken;
import com.zhuxi.user.module.interfaces.vo.user.UserLoginVO;
import com.zhuxi.user.module.interfaces.vo.user.UserRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.ZoneId;

/**
 * @author zhuxi
 */
@Mapper
public interface UserConvert {

    UserConvert COVERT = Mappers.getMapper(UserConvert.class);

    UserRegisterVO toUserRegisterVO(User user);

    @Mapping(target = "accessToken",source = "accessToken")
    @Mapping(target = "refreshToken",expression = "java(getRefreshToken(user))")
    @Mapping(target = "refreshExpireTime",expression = "java(getRefreshExpireTime(user))")
    @Mapping(target = "accessExpireTime",source = "accessExpireTime")
    UserLoginVO toLoginVO(User  user,String accessToken,Long accessExpireTime);

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
