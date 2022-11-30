package com.michael.csvapp.application.cli;

import com.michael.csvapp.infra.port.input.OrderUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCli {

    private static final Logger LOG = LoggerFactory.getLogger(OrderCli.class);

    private final OrderUseCase orderUseCase;

    @Autowired
    public OrderCli(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    public void importCsv(String filePath) {
        orderUseCase.importCsvFile(filePath);
        orderUseCase.indexation();
        String filePathExport = filePath.replaceFirst("\\.", "Result.");
        System.out.println("filePathExport = " + filePathExport);
        orderUseCase.exportCsvFile(filePathExport);
        orderUseCase.makeReport();
    }
}