package com.michael.csvapp.infra.port.output;

import com.michael.csvapp.domain.model.Orders;

import java.util.List;
import java.util.Map;

public interface OrderPort {

    void saveAll(List<Orders> orders);

    void addPrimaryKey();

    List<Map<String, Object>> countGroupBy(String fieldName);

    List selectAll();
}
