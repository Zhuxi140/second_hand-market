package com.zhuxi.usermodule.application.assembler;

import com.zhuxi.usermodule.domain.user.model.User;
import com.zhuxi.usermodule.interfaces.vo.user.UserLoginVO;
import com.zhuxi.usermodule.interfaces.vo.user.UserRegisterVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-16T16:58:04+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18 (Oracle Corporation)"
)
public class UserConvertImpl implements UserConvert {

    @Override
    public UserRegisterVO toUserRegisterVO(User user) {
        if ( user == null ) {
            return null;
        }

        UserRegisterVO userRegisterVO = new UserRegisterVO();

        userRegisterVO.setUserSn( user.getUserSn() );
        userRegisterVO.setNickname( user.getNickname() );
        userRegisterVO.setRole( user.getRole() );
        userRegisterVO.setAvatar( user.getAvatar() );

        return userRegisterVO;
    }

    @Override
    public UserLoginVO toLoginVO(User user, String accessToken, Long accessExpireTime) {
        if ( user == null && accessToken == null && accessExpireTime == null ) {
            return null;
        }

        UserLoginVO userLoginVO = new UserLoginVO();

        if ( user != null ) {
            userLoginVO.setUserSn( user.getUserSn() );
            userLoginVO.setNickname( user.getNickname() );
            userLoginVO.setRole( user.getRole() );
            userLoginVO.setAvatar( user.getAvatar() );
        }
        userLoginVO.setAccessToken( accessToken );
        userLoginVO.setAccessExpireTime( accessExpireTime );
        userLoginVO.setRefreshToken( getRefreshToken(user) );
        userLoginVO.setRefreshExpireTime( getRefreshExpireTime(user) );

        return userLoginVO;
    }
}
