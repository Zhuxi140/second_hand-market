package com.zhuxi.product.module.infrastructure.typehandler;

import com.zhuxi.common.handler.AbsTypeHandler.StringTypeHandler;
import com.zhuxi.product.module.domain.objectValue.ProductSn;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@MappedTypes(ProductSn.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@Component
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
