package com.example.spring.dbtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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

    @Test
    void innerCommit() {
        log.info("start external transaction");
        TransactionStatus outer = transactionManager.getTransaction(new DefaultTransactionDefinition());
        log.info("outer.isNewTransaction()={}", outer.isNewTransaction());

        log.info("start internal transaction");
        TransactionStatus inner = transactionManager.getTransaction(new DefaultTransactionDefinition());
        // o.s.j.d.DataSourceTransactionManager     : Participating in existing transaction
        log.info("inner.isNewTransaction()={}", inner.isNewTransaction()); // 내부 트랜잭션은 외부 트랜잭션에 포함되어 있음

        log.info("commit internal transaction");
        transactionManager.commit(inner);

        log.info("commit external transaction");
        transactionManager.commit(outer); // commit 은 외부 트랜잭션이 실행되는 시점에 한다. // 즉 내부 transaction commit 은 의미 없음

        Assertions.assertThat(outer.isNewTransaction()).isTrue();
        Assertions.assertThat(inner.isNewTransaction()).isFalse();
    }
}
