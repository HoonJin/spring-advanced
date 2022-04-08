package com.example.spring.proxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeDecorator implements Component {

    private final Component component;

    @Override
    public String operation() {
        long start = System.currentTimeMillis();
        String result = component.operation();
        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("time decorator operation = {}ms", duration);
        return result;
    }
}
