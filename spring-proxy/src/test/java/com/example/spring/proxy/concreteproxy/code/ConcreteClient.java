package com.example.spring.proxy.concreteproxy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ConcreteClient {

    private final ConcreteLogic concreteLogic;

    public void execute() {
//        log.info("");
        concreteLogic.operation();
    }
}
