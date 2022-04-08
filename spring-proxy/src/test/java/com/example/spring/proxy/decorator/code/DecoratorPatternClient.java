package com.example.spring.proxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DecoratorPatternClient {

    private final Component component;

    public void execute() {
        String operation = component.operation();
        log.info("operation result={}", operation);
    }
}
