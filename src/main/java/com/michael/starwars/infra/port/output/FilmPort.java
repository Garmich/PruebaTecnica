package com.michael.starwars.infra.port.output;

import com.michael.csvapp.domain.model.Orders;

import java.util.List;

public interface FilmPort {
    void saveAll(List<Orders> orders);

}
