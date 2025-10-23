package com.zhuxi.user.module.infrastructure.typehandler;

import com.zhuxi.common.interfaces.AbsTypeHandler.StringTypeHandler;
import com.zhuxi.user.module.domain.user.valueObject.Phone;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@MappedTypes(Phone.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@Component
public class PhoneTypeHandler extends StringTypeHandler<Phone> {
    @Override
    protected Phone fromString(String value) {
        return new Phone(value);
    }

    @Override
    protected String toString(Phone phone) {
        return phone.getNumber();
    }
}
