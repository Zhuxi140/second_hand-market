package com.zhuxi.user.module.infrastructure.typehandler;

import com.zhuxi.common.handler.AbsTypeHandler.EnumTypeHandler;
import com.zhuxi.user.module.domain.address.enums.IsDefault;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@MappedTypes(IsDefault.class)
@MappedJdbcTypes(JdbcType.TINYINT)
@Component
public class IsDefaultTypeHandler extends EnumTypeHandler<IsDefault> {
    @Override
    protected Integer toInteger(IsDefault value) {
        return value.getCode();
    }

    @Override
    protected IsDefault fromInteger(Integer code) {
        return IsDefault.getByCode(code);
    }
}
