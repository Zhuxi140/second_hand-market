package com.zhuxi.productmodule.infrastructure.typeHandler;

import com.zhuxi.common.handler.AbsTypeHandler.StringTypeHandler;
import com.zhuxi.productmodule.domain.objectValue.Title;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;


/**
 * @author zhuxi
 */
@MappedTypes(Title.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
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
