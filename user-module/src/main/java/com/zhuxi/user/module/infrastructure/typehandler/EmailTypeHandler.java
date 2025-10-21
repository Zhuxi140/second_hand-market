package com.zhuxi.user.module.infrastructure.typehandler;

import com.zhuxi.common.handler.AbsTypeHandler.StringTypeHandler;
import com.zhuxi.user.module.domain.user.valueObject.Email;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@MappedTypes(Email.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@Component
public class EmailTypeHandler extends StringTypeHandler<Email> {
    @Override
    protected Email fromString(String value) {
        return new Email(value);
    }

    @Override
    protected String toString(Email email) {
        return email.getAddress();
    }
}
