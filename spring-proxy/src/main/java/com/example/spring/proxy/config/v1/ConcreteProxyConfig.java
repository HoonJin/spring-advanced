package com.example.spring.proxy.config.v1;

import com.example.spring.proxy.app.v2.OrderControllerV2;
import com.example.spring.proxy.app.v2.OrderRepositoryV2;
import com.example.spring.proxy.app.v2.OrderServiceV2;
import com.example.spring.proxy.config.v1.concreteproxy.OrderControllerConcreteProxy;
import com.example.spring.proxy.config.v1.concreteproxy.OrderRepositoryConcreteProxy;
import com.example.spring.proxy.config.v1.concreteproxy.OrderServiceConcreteProxy;
import com.example.spring.proxy.trace.logtrace.LogTrace;
import com.example.spring.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace trace) {
        return new OrderRepositoryConcreteProxy(new OrderRepositoryV2(), trace);
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace trace) {
        return new OrderServiceConcreteProxy(new OrderServiceV2(orderRepositoryV2(trace)), trace);
    }

    @Bean
    public OrderControllerV2 orderController(LogTrace trace) {
        return new OrderControllerConcreteProxy(new OrderControllerV2(orderServiceV2(trace)), trace);
    }
}
