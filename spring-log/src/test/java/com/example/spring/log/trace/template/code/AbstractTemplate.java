package com.example.spring.log.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {

    public void execute() {
        long start = System.currentTimeMillis();
        call();
        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("end duration = {}", duration);
    }

    protected abstract void call();
}
