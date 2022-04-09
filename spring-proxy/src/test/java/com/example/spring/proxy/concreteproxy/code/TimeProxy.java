package com.example.spring.proxy.concreteproxy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeProxy extends ConcreteLogic {

    private final ConcreteLogic concreteLogic;

    @Override
    public String operation() {
        long start = System.currentTimeMillis();
        String result = concreteLogic.operation();
        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("time decorator operation = {}ms", duration);
        return result;
    }
}
