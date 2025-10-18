package com.zhuxi.usermodule.infrastructure.typehandler;

import com.zhuxi.common.handler.AbsTypeHandler.EnumTypeHandler;
import com.zhuxi.usermodule.domain.user.enums.UserStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author zhuxi
 */
@MappedTypes(UserStatus.class)
@MappedJdbcTypes(JdbcType.TINYINT)
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
