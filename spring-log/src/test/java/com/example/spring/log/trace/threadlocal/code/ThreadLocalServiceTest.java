package com.example.spring.log.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ThreadLocalServiceTest {

    private ThreadLocalService threadLocalService = new ThreadLocalService();

    @Test
    void field() {
        log.info("start");

        Runnable t1 = () -> threadLocalService.logic("test1");
        Runnable t2 = () -> {
            threadLocalService.logic("test2");
        };

        Thread thread1 = new Thread(t1);
        thread1.setName("THREAD1");
        Thread thread2 = new Thread(t2);
        thread2.setName("THREAD2");

        thread1.start();
        sleep(100);
        thread2.start();
        sleep(2000);

        log.info("end");
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}