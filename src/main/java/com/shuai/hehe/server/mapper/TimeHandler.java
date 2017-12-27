package com.shuai.hehe.server.mapper;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

/**
 *
 */
public class TimeHandler implements TypeHandler<Long> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Long parameter, JdbcType jdbcType) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long getResult(ResultSet rs, String columnName) throws SQLException {
        return toLong(rs.getTimestamp(columnName));
    }

    @Override
    public Long getResult(ResultSet rs, int columnIndex) throws SQLException {
        return toLong(rs.getTimestamp(columnIndex));
    }

    @Override
    public Long getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toLong(cs.getTimestamp(columnIndex));
    }

    private Long toLong(Timestamp timestamp){
        return timestamp.getTime();
    }
}
