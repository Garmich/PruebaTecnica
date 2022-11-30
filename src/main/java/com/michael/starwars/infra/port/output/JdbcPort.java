package com.michael.starwars.infra.port.output;

import java.util.List;

public interface JdbcPort {
    <T> T save( T reg );

    <T> T getById( String id, Class<T> clazz );

    <T> List<T> getAll(Class<T> clazz );

    <T> List<T> query(String sql, Class<T> clazz);
}
