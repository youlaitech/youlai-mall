package com.youlai.common.mybatis.handler.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CommaStringListHandler extends BaseTypeHandler<List<String>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        String joinedValues = String.join(",", parameter);
        ps.setString(i, joinedValues);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String values = rs.getString(columnName);
        return values != null ? Arrays.asList(values.split(",")) : null;
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String values = rs.getString(columnIndex);
        return values != null ? Arrays.asList(values.split(",")) : null;
    }

    @Override
    public List<String> getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        String values = cs.getString(columnIndex);
        return values != null ? Arrays.asList(values.split(",")) : null;
    }
}
