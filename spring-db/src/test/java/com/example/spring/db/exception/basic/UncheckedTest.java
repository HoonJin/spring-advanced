package com.example.spring.db.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class UncheckedTest {

    Service service = new Service();

    @Test
    void unchecked_catch() {
        service.callCatch();
    }

    @Test
    void unchecked_throws() {
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }

    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    static class Repository {
        public void call() {
            throw new MyUncheckedException("EX");
        }
    }

    static class Service {
        private final Repository repository = new Repository();

        // 필요하면 예외를 잡아서 처리
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("handle exception", e);
            }
        }

        public void callThrow() {
            repository.call();
        }
    }

}