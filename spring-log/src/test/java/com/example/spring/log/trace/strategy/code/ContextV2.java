package com.example.spring.log.trace.strategy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2 {

    public void execute(Strategy strategy) {
        long start = System.currentTimeMillis();
        strategy.call();
        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("end duration = {}", duration);
    }


}
