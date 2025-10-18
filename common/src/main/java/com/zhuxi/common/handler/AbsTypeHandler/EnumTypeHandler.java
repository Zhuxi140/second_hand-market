package com.zhuxi.common.handler.AbsTypeHandler;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhuxi
 */
public abstract class EnumTypeHandler<T> extends BaseTypeHandler<T> {

    protected abstract Integer toInteger(T value);
    protected abstract T fromInteger(Integer code);


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
