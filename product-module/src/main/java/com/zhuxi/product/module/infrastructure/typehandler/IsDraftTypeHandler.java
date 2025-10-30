package com.zhuxi.product.module.infrastructure.typehandler;

import com.zhuxi.common.interfaces.AbsTypeHandler.EnumTypeHandler;
import com.zhuxi.product.module.domain.enums.IsDraft;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */

@MappedTypes(IsDraft.class)
@MappedJdbcTypes(JdbcType.TINYINT)
@Component
public class IsDraftTypeHandler extends EnumTypeHandler<IsDraft> {
    @Override
    protected Integer toInteger(IsDraft value) {
        return value.getCode();
    }

    @Override
    protected IsDraft fromInteger(Integer code) {
        return IsDraft.getByCode(code);
    }
}
