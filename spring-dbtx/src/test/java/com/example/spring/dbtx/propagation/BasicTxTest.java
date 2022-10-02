package com.example.spring.dbtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    PlatformTransactionManager transactionManager;

    @TestConfiguration
    static class Config {

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit() {
        log.info("start transaction");
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        log.info("start commit");
        transactionManager.commit(status);
        log.info("commit is done");
    }

    @Test
    void rollback() {
        log.info("start transaction");
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        log.info("start rollback");
        transactionManager.rollback(status);
        log.info("rollback is done");
    }

    @Test
    void doubleCommit() {
        log.info("start transaction 1");
        TransactionStatus status1 = transactionManager.getTransaction(new DefaultTransactionDefinition());

        log.info("start commit 1");
        transactionManager.commit(status1);

        log.info("start transaction 2");
        TransactionStatus status2 = transactionManager.getTransaction(new DefaultTransactionDefinition());

        log.info("start commit 2");
        transactionManager.commit(status2);
    }

    @Test
    void commitAndRollback() {
        log.info("start transaction 1");
        TransactionStatus status1 = transactionManager.getTransaction(new DefaultTransactionDefinition());

        log.info("start commit 1");
        transactionManager.commit(status1);

        log.info("start transaction 2");
        TransactionStatus status2 = transactionManager.getTransaction(new DefaultTransactionDefinition());

        log.info("start rollback 2");
        transactionManager.rollback(status2);
    }
}
