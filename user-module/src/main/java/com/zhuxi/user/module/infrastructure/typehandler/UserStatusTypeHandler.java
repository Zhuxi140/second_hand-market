package com.zhuxi.user.module.infrastructure.typehandler;

import com.zhuxi.common.interfaces.AbsTypeHandler.EnumTypeHandler;
import com.zhuxi.user.module.domain.user.enums.UserStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@MappedTypes(UserStatus.class)
@MappedJdbcTypes(JdbcType.TINYINT)
@Component
public class UserStatusTypeHandler extends EnumTypeHandler<UserStatus> {

    @Override
    protected Integer toInteger(UserStatus value) {
        return value.getCode();
    }

    @Override
    protected UserStatus fromInteger(Integer code) {
        return UserStatus.getByCode(code);
    }
}
