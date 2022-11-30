package com.michael.starwars.infra.repository;

import com.michael.csvapp.domain.model.Orders;
import com.michael.starwars.infra.port.output.FilmPort;
import com.michael.starwars.infra.port.output.JdbcPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmRepository implements FilmPort {
    @Autowired
    private JdbcPort jdbcPort;

    public void saveAll(List<Orders> orders) {
        orders.forEach(jdbcPort::save);
    }
}
