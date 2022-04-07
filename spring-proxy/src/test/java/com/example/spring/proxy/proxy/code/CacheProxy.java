package com.example.spring.proxy.proxy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CacheProxy implements Subject {

    private final Subject subject;

    private String cacheValue;

    @Override
    public String operation() {
        log.info("call proxy");
        if (cacheValue == null) {
            cacheValue = subject.operation();
        }
        return cacheValue;
    }
}
