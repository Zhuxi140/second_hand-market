package com.zhuxi.userModule.application.assembler;

import com.zhuxi.userModule.domain.user.model.User;
import com.zhuxi.userModule.interfaces.vo.user.UserLoginVO;
import com.zhuxi.userModule.interfaces.vo.user.UserRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zhuxi
 */
@Mapper
public interface UserConvert {

    UserConvert COVERT = Mappers.getMapper(UserConvert.class);

    UserRegisterVO toUserRegisterVO(User user);

    UserLoginVO toLoginVO(User  user);

}
