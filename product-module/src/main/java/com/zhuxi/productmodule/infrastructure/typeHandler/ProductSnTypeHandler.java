package com.zhuxi.productmodule.infrastructure.typeHandler;

import com.zhuxi.common.handler.AbsTypeHandler.StringTypeHandler;
import com.zhuxi.productmodule.domain.objectValue.ProductSn;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author zhuxi
 */
@MappedTypes(ProductSn.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ProductSnTypeHandler extends StringTypeHandler<ProductSn> {
    @Override
    protected ProductSn fromString(String value) {
        return new ProductSn( value);
    }

    @Override
    protected String toString(ProductSn value) {
        return value.getSn();
    }
}
