package com.zhuxi.product.module.infrastructure.typehandler;

import com.zhuxi.common.interfaces.AbsTypeHandler.EnumTypeHandler;
import com.zhuxi.product.module.domain.enums.ProductStatus;
import org.apache.ibatis.type.*;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@MappedTypes(ProductStatus.class)
@MappedJdbcTypes(JdbcType.TINYINT)
@Component
public class ProductStatusTypeHandler extends EnumTypeHandler<ProductStatus> {


    @Override
    protected Integer toInteger(ProductStatus value) {
        return value.getCode();
    }

    @Override
    protected ProductStatus fromInteger(Integer code) {
        return ProductStatus.getByCode(code);
    }
}
