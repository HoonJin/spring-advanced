package com.example.spring.proxy.config.v3;

import com.example.spring.proxy.app.v1.*;
import com.example.spring.proxy.app.v2.OrderControllerV2;
import com.example.spring.proxy.app.v2.OrderRepositoryV2;
import com.example.spring.proxy.app.v2.OrderServiceV2;
import com.example.spring.proxy.config.v3.advice.LogTraceAdvice;
import com.example.spring.proxy.trace.logtrace.LogTrace;
import com.example.spring.proxy.trace.logtrace.ThreadLocalLogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV2 {

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace trace) {
        OrderRepositoryV2 orderRepositoryV2 = new OrderRepositoryV2();
        ProxyFactory proxyFactory = new ProxyFactory(orderRepositoryV2);
        proxyFactory.addAdvisor(getAdvisor(trace));
        return (OrderRepositoryV2) proxyFactory.getProxy();

    }

    private Advisor getAdvisor(LogTrace trace) {
        // point cut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        LogTraceAdvice advice = new LogTraceAdvice(trace);
        return new DefaultPointcutAdvisor(pointcut, advice);

    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace trace) {
        OrderServiceV2 orderServiceV2 = new OrderServiceV2(orderRepositoryV2(trace));
        ProxyFactory proxyFactory = new ProxyFactory(orderServiceV2);
        proxyFactory.addAdvisor(getAdvisor(trace));
        return (OrderServiceV2) proxyFactory.getProxy();
    }

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace trace) {
        OrderControllerV2 orderControllerV2 = new OrderControllerV2(orderServiceV2(trace));
        ProxyFactory proxyFactory = new ProxyFactory(orderControllerV2);
        proxyFactory.addAdvisor(getAdvisor(trace));
        return (OrderControllerV2) proxyFactory.getProxy();
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
