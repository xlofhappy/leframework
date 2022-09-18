package org.le.uid.generator.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.le.uid.generator.worker.service.pojo.WorkerNodeType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author xiaole
 */
@MappedTypes(value = WorkerNodeType.class)
public class WorkerNodeTypeHandler extends BaseTypeHandler<WorkerNodeType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, WorkerNodeType parameter,
                                    JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.value());
    }

    /**
     * how to deal the data via column name
     */
    @Override
    public WorkerNodeType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int type = rs.getInt(columnName);
        return Arrays.stream(WorkerNodeType.values()).findAny().filter(p -> p.value() == type).orElse(null);
    }

    /**
     * how to deal the data via column index
     */
    @Override
    public WorkerNodeType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int type = rs.getInt(columnIndex);
        return Arrays.stream(WorkerNodeType.values()).findAny().filter(p -> p.value() == type).orElse(null);
    }

    /**
     * how to deal the data when procedure
     */
    @Override
    public WorkerNodeType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int type = cs.getInt(columnIndex);
        return Arrays.stream(WorkerNodeType.values()).findAny().filter(p -> p.value() == type).orElse(null);
    }
}
