package com.zhuxi.userModule.infrastructure.typehandler;

import com.zhuxi.common.handler.AbstractValueObjectTypeHandler;
import com.zhuxi.userModule.domain.user.valueObject.Phone;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

@MappedTypes(Phone.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@Component
public class PhoneTypeHandler extends AbstractValueObjectTypeHandler<Phone> {
    @Override
    protected Phone fromString(String value) {
        return new Phone(value);
    }

    @Override
    protected String toString(Phone phone) {
        return phone.getNumber();
    }
}
