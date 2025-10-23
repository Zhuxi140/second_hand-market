package com.zhuxi.product.module.infrastructure.typehandler;

import com.zhuxi.common.interfaces.AbsTypeHandler.StringTypeHandler;
import com.zhuxi.product.module.domain.objectValue.Title;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;


/**
 * @author zhuxi
 */
@MappedTypes(Title.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@Component
public class TitleTypeHandler extends StringTypeHandler<Title> {
    @Override
    protected Title fromString(String value) {
        return new Title(value);
    }

    @Override
    protected String toString(Title value) {
        return value.getTitle();
    }
}
