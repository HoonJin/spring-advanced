package com.example.spring.db.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CheckedTest {

    Service service = new Service();

    @Test
    void checked_catch() {
        service.callCatch();
    }

    @Test
    void checked_throws() {
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }

    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    static class Repository {
        public void call() throws MyCheckedException {
            throw new MyCheckedException("EX");
        }
    }

    static class Service {
        private final Repository repository = new Repository();

        // 예외를 잡아서 처리
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                log.info("handle exception", e);
            }
        }

        // 발생할 수 있는 예외를 명시해야 한다
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

}