package com.michael.csvapp.infra.port.output;

import java.util.List;

public interface JdbcBatchPort {
    <T> void saveAll(List<T> regs);
}
