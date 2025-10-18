package com.zhuxi.usermodule.infrastructure.typehandler;

import com.zhuxi.common.handler.AbsTypeHandler.StringTypeHandler;
import com.zhuxi.usermodule.domain.user.valueObject.Username;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author zhuxi
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(Username.class)
public class UserNameTypeHandler extends StringTypeHandler<Username> {
    @Override
    protected Username fromString(String value) {
        return new Username(value);
    }

    @Override
    protected String toString(Username username) {
        return username.getAccountName();
    }
}
