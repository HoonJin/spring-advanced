package com.example.spring.proxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageDecorator implements Component {

    private final Component component;

    @Override
    public String operation() {
        log.info("message decorator operation");
        return "***" + component.operation() + "***";
    }
}
