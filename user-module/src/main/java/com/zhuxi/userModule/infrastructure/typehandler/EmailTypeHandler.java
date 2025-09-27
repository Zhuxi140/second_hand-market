package com.zhuxi.userModule.infrastructure.typehandler;

import com.zhuxi.common.handler.AbstractValueObjectTypeHandler;
import com.zhuxi.userModule.domain.user.valueObject.Email;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

@MappedTypes(Email.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@Component
public class EmailTypeHandler extends AbstractValueObjectTypeHandler<Email> {
    @Override
    protected Email fromString(String value) {
        return new Email(value);
    }

    @Override
    protected String toString(Email email) {
        return email.getAddress();
    }
}
