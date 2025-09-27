package com.zhuxi.userModule.infrastructure.typehandler;

import com.zhuxi.common.handler.AbstractValueObjectTypeHandler;
import com.zhuxi.userModule.domain.user.valueObject.Username;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(Username.class)
@Component
public class UserNameTypeHandler extends AbstractValueObjectTypeHandler<Username> {
    @Override
    protected Username fromString(String value) {
        return new Username(value);
    }

    @Override
    protected String toString(Username username) {
        return username.getAccountName();
    }
}
