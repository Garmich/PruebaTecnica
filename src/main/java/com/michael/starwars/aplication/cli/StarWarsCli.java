package com.michael.starwars.aplication.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StarWarsCli {

    private static final Logger LOG = LoggerFactory.getLogger(com.michael.csvapp.application.cli.OrderCli.class);

//    private final OrderInputPort orderInputPort;
//
//    @Autowired
//    public OrderCli(OrderInputPort orderInputPort) {
//        this.orderInputPort = orderInputPort;
//    }
//
//    public void importCsv(String filePath) {
//        orderInputPort.importCsvFile(filePath);
//        orderInputPort.indexation();
//        String filePathExport = filePath.replaceFirst("\\.", "Result.");
//        System.out.println("filePathExport = " + filePathExport);
//        orderInputPort.exportCsvFile(filePathExport);
//        orderInputPort.makeReport();
//    }
}