package com.michael.starwars.infra.repository;

import com.michael.starwars.infra.port.output.JdbcPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class MysqlRepository implements JdbcPort {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public MysqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T save(T reg) {
//
//        List<Field> entityFields = Arrays.asList(reg.getClass().getDeclaredFields());
//
//        List<String> fields = new ArrayList<>();
//        List<Object> fieldValues = new ArrayList<>();
//        entityFields.forEach(field -> {
//            fields.add(field.getName());
//            fieldValues.add(valueField(field, reg));
//        });
//        System.out.println("fieldValues = " + fieldValues);
//        String sql = "INSERT INTO " +
//                reg.getClass().getSimpleName() +
//                "(" + String.join(",", fields) + ")" +
//                " VALUES " +
//                "(" + String.join(",", Collections.nCopies(fields.size(), "?")) + ")";
//
//        jdbcTemplate.update(sql, fieldValues);
//
//        return reg;

        Field[] entityFields = reg.getClass().getDeclaredFields();

        String[] fields = new String[ entityFields.length ];
        Object[] fieldValues = new Object[ entityFields.length ];

        try {
            for ( int i=0; i<entityFields.length; i++ ) {
                fields[i] = entityFields[i].getName();
                fieldValues[i] = reg.getClass()
                        .getMethod( "get"+entityFields[i].getName().substring(0,1).toUpperCase()+entityFields[i].getName().substring(1) )
                        .invoke( reg );
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
                .append( reg.getClass().getSimpleName() )
                .append( "(" ).append( String.join( ",", fields) ).append( ")" )
                .append( " VALUES " )
                .append( "(" ).append( String.join( ",", Collections.nCopies( fields.length, "?") ) ).append( ")" );

        jdbcTemplate.update(sql.toString(), fieldValues);

        return reg;
    }

    private <T> Object valueField(Field field, T reg) {
        try {
            return reg.getClass()
                    .getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))
                    .invoke(reg);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw 
                    new RuntimeException(e);
        }
    }


    @Override
    public <T> T getById(String id, Class<T> clazz) {
        List<T> list = jdbcTemplate.query("SELECT * FROM "+clazz.getSimpleName()+" WHERE id = ?",
                new LombokRowMapper<>(clazz),
                id );

        if ( !list.isEmpty() ) return list.get(0);

        return null;
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        System.out.println("jdbcTemplate = " + jdbcTemplate);
        System.out.println(clazz.getSimpleName());
        LombokRowMapper<Object> b = new LombokRowMapper<>(clazz);
        System.out.println("b = " + "SELECT id FROM " + clazz.getSimpleName());
        List<T> a = jdbcTemplate.query("SELECT * FROM " + clazz.getSimpleName(), new LombokRowMapper<>(clazz));
        System.out.println("a = " + a.size());
        return a;
    }

    @Override
    public <T> List<T> query(String sql, Class<T> clazz) {
        return jdbcTemplate.query(sql, new LombokRowMapper<>(clazz));
    }

    private static class LombokRowMapper<T> implements RowMapper<T> {
        private final Class<?> clazz;

        public LombokRowMapper( Class<?> clazz ) {
            this.clazz = clazz;
        }

        @Override
        public String toString() {
            return "LombokRowMapper{" +
                    "clazz=" + clazz +
                    '}';
        }

        @Override
        public T mapRow(ResultSet rs, int rowNum) throws SQLException {

            try {
                Method builderMethod = clazz.getMethod("builder");

                Object row = builderMethod.invoke(null);
                Method[] methods = row.getClass().getDeclaredMethods();

                for (Method method : methods) mapMethods(rs, row, method);

                T p = (T) row.getClass().getMethod("build").invoke(row);
                System.out.println("p = " + p);
                return p;
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                     | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    private static void mapMethods(ResultSet rs, Object row, Method method) throws SQLException, IllegalAccessException, InvocationTargetException {
        int pos;
        pos = rs.findColumn(method.getName());

        if (pos != -1) {
            Object fieldValue = rs.getObject(pos);

            method.invoke(row, fieldValue);
        }
    }

}
