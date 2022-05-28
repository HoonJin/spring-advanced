package com.example.spring.aop.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {

//    private final ApplicationContext applicationContext;
    private final ObjectProvider<CallServiceV2> callServiceV2ObjectProvider;

    public void external() {
        log.info("call external");
//        CallServiceV2 service = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 service = callServiceV2ObjectProvider.getObject();
        service.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
