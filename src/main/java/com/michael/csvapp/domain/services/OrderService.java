package com.michael.csvapp.domain.services;

import com.michael.csvapp.domain.model.Orders;
import com.michael.csvapp.infra.port.input.OrderUseCase;
import com.michael.csvapp.infra.port.output.OrderPort;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderService implements OrderUseCase {
    Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderPort orderPort;

    public void importCsvFile(String filePath){
        logger.info("Parser csv lines");
        List<Orders> orders = readCsvFile(filePath)
                .stream().distinct().collect(Collectors.toList());
        logger.info("Saving list of orders of size {}", orders.size());
        orderPort.saveAll(orders);
    }

    public void makeReport() {
        logger.info("Make report {} table by id", Orders.class.getSimpleName());
        System.out.println("----------------Regions-----------------------");
        List<Map<String, Object>> regions = orderPort.countGroupBy(Orders.Fields.region);
        regions.forEach(System.out::println);
        System.out.println("----------------Countries-----------------------");
        List<Map<String, Object>> countries = orderPort.countGroupBy(Orders.Fields.country);
        countries.forEach(System.out::println);
        System.out.println("----------------Item Types-----------------------");
        List<Map<String, Object>> itemTypes = orderPort.countGroupBy(Orders.Fields.itemType);
        itemTypes.forEach(System.out::println);
        System.out.println("----------------Sales Channel-----------------------");
        List<Map<String, Object>> salesChannel = orderPort.countGroupBy(Orders.Fields.salesChannel);
        salesChannel.forEach(System.out::println);
        System.out.println("----------------Order Priority-----------------------");
        List<Map<String, Object>> orderPriority = orderPort.countGroupBy(Orders.Fields.priority);
        orderPriority.forEach(System.out::println);
    }

    @Override
    public void indexation() {
        logger.info("Index {} table by id", Orders.class.getSimpleName());
        orderPort.addPrimaryKey();
    }

    public void exportCsvFile(String filePath) {
        List<Orders> orders = orderPort.selectAll();
        System.out.println("orders = " + orders.size());
        writeCsvFile(filePath, orders);
    }

    private static void writeCsvFile(String filePath, List<Orders> orders) {
        try (Writer writer  = new FileWriter(filePath)) {

            StatefulBeanToCsv<Orders> sbc = new StatefulBeanToCsvBuilder<Orders>(writer)
                    .withQuotechar('\'')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            sbc.write(orders);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Orders> readCsvFile(String filePath) {
        try {
            return new CsvToBeanBuilder(new FileReader(filePath))
                    .withType(Orders.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
