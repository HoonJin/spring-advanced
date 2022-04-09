package com.example.spring.proxy.config.v1.concreteproxy;

import com.example.spring.proxy.app.v2.OrderControllerV2;
import com.example.spring.proxy.trace.TraceStatus;
import com.example.spring.proxy.trace.logtrace.LogTrace;

public class OrderControllerConcreteProxy extends OrderControllerV2 {

    private final OrderControllerV2 target;
    private final LogTrace trace;

    public OrderControllerConcreteProxy(OrderControllerV2 target, LogTrace trace) {
        super(null);
        this.target = target;
        this.trace = trace;
    }

    @Override
    public String request(String itemId) {
        TraceStatus status = trace.begin("OrderController.request()");
        try {
            String request = target.request(itemId);
            trace.end(status);
            return request;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        return target.noLog();
    }
}
