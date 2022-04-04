package com.example.spring.log.trace.strategy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ContextV1 {

    private final Strategy strategy;

    public void execute() {
        long start = System.currentTimeMillis();
        strategy.call();
        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("end duration = {}", duration);
    }


}
