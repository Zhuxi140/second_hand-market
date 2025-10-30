package com.zhuxi.product.module.infrastructure.typehandler;

import com.zhuxi.common.interfaces.AbsTypeHandler.EnumTypeHandler;
import com.zhuxi.product.module.domain.enums.ImageType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@MappedTypes(ImageType.class)
@MappedJdbcTypes(JdbcType.TINYINT)
@Component
public class ImageTypeTypeHandler extends EnumTypeHandler<ImageType> {
    @Override
    protected Integer toInteger(ImageType value) {
        return value.getCode();
    }

    @Override
    protected ImageType fromInteger(Integer code) {
        return ImageType.getByCode(code);
    }
}
