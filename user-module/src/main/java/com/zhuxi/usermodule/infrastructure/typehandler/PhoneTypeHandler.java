package com.zhuxi.usermodule.infrastructure.typehandler;

import com.zhuxi.common.handler.AbsTypeHandler.StringTypeHandler;
import com.zhuxi.usermodule.domain.user.valueObject.Phone;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@MappedTypes(Phone.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
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
