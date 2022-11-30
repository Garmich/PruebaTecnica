package com.michael.csvapp.infra.configurations;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MysqlBatchPreparedStatementSetter<T> implements BatchPreparedStatementSetter {

    private final List<T> regs;

    public MysqlBatchPreparedStatementSetter(List<T> regs) {
        super();
        this.regs = regs;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) {
        try {
            T reg = regs.get(i);
            Field[] entityFields = reg.getClass().getDeclaredFields();
            int valuePos = 1;
            for (Field field : entityFields) {
                ps.setObject(valuePos, valueField(field, reg));
                valuePos++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getBatchSize() {
        return regs.size();
    }

    private Object valueField( Field field, T reg) {
        try {
            return reg.getClass()
                    .getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))
                    .invoke(reg);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
