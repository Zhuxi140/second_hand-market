package com.zhuxi.user.module.infrastructure.typehandler;

import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.interfaces.AbsTypeHandler.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * @author zhuxi
 */
@MappedTypes(Role.class)
@MappedJdbcTypes(JdbcType.INTEGER)
@Component
public class RoleTypeHandler extends EnumTypeHandler<Role> {
    @Override
    protected Integer toInteger(Role value) {
        return value.getId();
    }

    @Override
    protected Role fromInteger(Integer code) {
        return Role.getRoleById(code);
    }
}