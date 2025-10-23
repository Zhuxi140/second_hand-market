package com.zhuxi.common.interfaces.AbsTypeHandler;


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
        if (parameter == null){
            ps.setNull(i, jdbcType.TYPE_CODE);
        }else{
            ps.setInt(i, toInteger(parameter));
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        if (rs.wasNull()){
            return null;
        }
        return fromInteger(code);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        if (rs.wasNull()){
            return null;
        }
        return fromInteger(code);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        if (cs.wasNull()){
            return null;
        }
        return fromInteger(code);
    }
}
