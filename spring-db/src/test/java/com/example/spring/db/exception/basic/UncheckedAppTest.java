package com.example.spring.db.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

@Slf4j
public class UncheckedAppTest {

    Controller controller = new Controller();

    @Test
    void unchecked() {
        Assertions.assertThatThrownBy(() -> controller.request())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void printException() {
        try {
            controller.request();
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    static class NetworkClient {
        public void call() {
            throw new RuntimeConnectException("CONNECT EX");
        }
    }

    static class Repository {
        public void call() {
            try {
                execute();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
            }

        }

        public void execute() throws SQLException {
            throw new SQLException();
        }
    }

    static class Service {
        private final Repository repository = new Repository();
        private final NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    static class Controller {
        private final Service service = new Service();

        public void request() {
            service.logic();
        }
    }

    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }
}
