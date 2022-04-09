package com.example.spring.proxy.config.v1.concreteproxy;

import com.example.spring.proxy.app.v2.OrderRepositoryV2;
import com.example.spring.proxy.trace.TraceStatus;
import com.example.spring.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryConcreteProxy extends OrderRepositoryV2 {

    private final OrderRepositoryV2 target;
    private final LogTrace trace;

    @Override
    public void save(String itemId) {
        TraceStatus status = trace.begin("OrderRepository.save()");
        try {
            target.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
