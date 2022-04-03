package com.example.spring.log.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldService {

    private String nameStore;

    public String logic(String name) {
        log.info("save name={} -> nameStore={}", name, nameStore);
        nameStore = name;
        sleep(1000);
        log.info("read nameStore={}", nameStore);
        return nameStore;
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
