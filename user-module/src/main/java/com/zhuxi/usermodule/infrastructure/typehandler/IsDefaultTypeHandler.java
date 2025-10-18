package com.zhuxi.usermodule.infrastructure.typehandler;

import com.zhuxi.common.handler.AbsTypeHandler.EnumTypeHandler;
import com.zhuxi.usermodule.domain.address.enums.IsDefault;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author zhuxi
 */
@MappedTypes(IsDefault.class)
@MappedJdbcTypes(JdbcType.TINYINT)
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
