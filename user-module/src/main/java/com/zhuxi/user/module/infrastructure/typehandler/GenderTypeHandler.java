package com.zhuxi.user.module.infrastructure.typehandler;

import com.zhuxi.common.interfaces.AbsTypeHandler.EnumTypeHandler;
import com.zhuxi.user.module.domain.user.enums.Gender;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@MappedTypes(Gender.class)
@MappedJdbcTypes(JdbcType.TINYINT)
@Component
public class GenderTypeHandler extends EnumTypeHandler<Gender> {


    @Override
    protected Integer toInteger(Gender value) {
        return value.getCode();
    }

    @Override
    protected Gender fromInteger(Integer code) {
        return Gender.getByCode(code);
    }
}
