package com.zhuxi.userModule.application.assembler;

import com.zhuxi.userModule.domain.user.model.User;
import com.zhuxi.userModule.interfaces.vo.user.UserLoginVO;
import com.zhuxi.userModule.interfaces.vo.user.UserRegisterVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-09T09:19:56+0800",
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
    public UserLoginVO toLoginVO(User user) {
        if ( user == null ) {
            return null;
        }

        UserLoginVO userLoginVO = new UserLoginVO();

        userLoginVO.setUserSn( user.getUserSn() );
        userLoginVO.setNickname( user.getNickname() );
        userLoginVO.setRole( user.getRole() );
        userLoginVO.setAvatar( user.getAvatar() );

        return userLoginVO;
    }
}
