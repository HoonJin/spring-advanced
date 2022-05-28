package com.example.spring.aop.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

//    private CallServiceV1 callServiceV1;
//
//    @Autowired // 셀프 참조... 존나 구린 와중에 실행조차 되지 않음
//    public void setCallServiceV1(CallServiceV1 callServiceV1) {
//        this.callServiceV1 = callServiceV1;
//    }

    public void external() {
        log.info("call external");
//        callServiceV1.internal();
        internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
