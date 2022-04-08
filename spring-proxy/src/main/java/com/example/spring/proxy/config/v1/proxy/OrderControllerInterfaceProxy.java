package com.example.spring.proxy.config.v1.proxy;

import com.example.spring.proxy.app.v1.OrderControllerV1;
import com.example.spring.proxy.trace.TraceStatus;
import com.example.spring.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerV1 {

    private final OrderControllerV1 target;
    private final LogTrace trace;
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
