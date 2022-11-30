package com.michael.csvapp.infra.repository;

import com.michael.csvapp.domain.model.Orders;
import com.michael.csvapp.infra.port.output.JdbcBatchPort;
import com.michael.csvapp.infra.port.output.OrderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderRepository implements OrderPort {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcBatchPort repositoryBatchPort;

    private static final String TABLE = Orders.class.getSimpleName();

    @Override
    public void saveAll(List<Orders> orders) {
        repositoryBatchPort.saveAll(orders);
    }

    @Override
    public void addPrimaryKey() {
        String sql = "ALTER TABLE " +
                TABLE +
                " ADD PRIMARY KEY (id)";
        jdbcTemplate.execute(sql);
    }

    @Override
    public List selectAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE, new BeanPropertyRowMapper(Orders.class));
    }

    @Override
    public List<Map<String, Object>> countGroupBy(String fieldName) {
        String sql = "SELECT " +
                fieldName +
                ", COUNT(*) FROM " +
                TABLE +
                " GROUP BY " +
                fieldName + " ORDER BY " + fieldName;

        return jdbcTemplate.queryForList(sql);
    }
}
