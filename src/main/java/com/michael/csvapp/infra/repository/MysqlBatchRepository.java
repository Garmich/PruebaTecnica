package com.michael.csvapp.infra.repository;

import com.michael.csvapp.infra.configurations.MysqlBatchPreparedStatementSetter;
import com.michael.csvapp.infra.port.output.JdbcBatchPort;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.sql.BatchUpdateException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class MysqlBatchRepository implements JdbcBatchPort {
    private static final Logger log = LoggerFactory.getLogger(MysqlBatchRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${jdbc.batch_insert_size}")
    private int batchInsertSize;

    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> void saveAll(List<T> regs) {
        Class<?> clazz = regs.get(0).getClass();
        List<Field> entityFields = Arrays.asList(regs.get(0).getClass().getDeclaredFields());
        List<String> fields = entityFields.stream().map(Field::getName).collect(Collectors.toList());
        String insertSql = makeInsertStatement(clazz, fields);
        String updateSql = makeUpdateStatement(clazz, fields);

        CompletableFuture<Void> run = generateAsyncActions(regs, insertSql, updateSql);

        runFutures(run);
    }

    private static void runFutures(CompletableFuture<Void> run) {
        StopWatch timer = new StopWatch();
        try {
            timer.start();
            run.get();
            timer.stop();
            log.info("batchInsertAsync -> Total time in mili seconds: " + timer.getTime());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> CompletableFuture<Void> generateAsyncActions(List<T> regs, String insertSql, String updateSql) {
        final AtomicInteger sublists = new AtomicInteger();
        CompletableFuture[] futures = regs.stream()
                .collect(Collectors.groupingBy(t -> sublists.getAndIncrement() / batchInsertSize))
                .values()
                .stream()
                .map(ul -> runBatchSave(ul, insertSql, updateSql))
                .toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(futures);
    }

    public <T> CompletableFuture<Void> runBatchSave(List<T> regs, String insertSql, String updateSql) {

        return CompletableFuture.runAsync(() -> {
            try {
                jdbcTemplate.batchUpdate(insertSql, new MysqlBatchPreparedStatementSetter<>(regs));
            } catch (DataAccessException e) {
                if (!(e.getCause() instanceof BatchUpdateException)) {
                    throw new RuntimeException(e);
                }
                BatchUpdateException be = (BatchUpdateException) e.getCause();
                int[] batchRes = be.getUpdateCounts();
                if (batchRes != null && batchRes.length > 0) {
                    List<T> updaterRegs = IntStream.range(0, batchRes.length)
                                                .filter(index -> batchRes[index] == Statement.EXECUTE_FAILED)
                                                .mapToObj(regs::get)
                                                .collect(Collectors.toList());
                    jdbcTemplate.batchUpdate(updateSql, new MysqlBatchPreparedStatementSetter<>(updaterRegs));
                }
            }
        }, executor);
    }

    private static String makeUpdateStatement(Class<?> clazz, List<String> fields) {
        String lastField = fields.remove(fields.size() - 1);
        return "UPDATE "
                + clazz.getSimpleName()
                + " SET "
                + String.join(" = ?, ", fields)
                + " = ? WHERE " + lastField + " = ?";
    }

    private static String makeInsertStatement(Class<?> clazz, List<String> fields) {
        return "INSERT INTO " +
                clazz.getSimpleName() +
                " (" + String.join(", ", fields) + ")" +
                " VALUES " +
                "(" + String.join(", ", Collections.nCopies(fields.size(), "?")) + ")";
    }
}
