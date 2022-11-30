package com.michael.csvapp.infra.port.input;


public interface OrderUseCase {
    void importCsvFile(String filePath);
    void indexation();
    void makeReport();

    void exportCsvFile(String filePath);
}
