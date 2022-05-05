package com.example.spring.proxy.proxyfactory;

import com.example.spring.proxy.advice.TimeAdvice;
import com.example.spring.proxy.common.service.ConcreteService;
import com.example.spring.proxy.common.service.ServiceImpl;
import com.example.spring.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 jdk 동적프록시 사용")
    void interfaceProxy() {
        ServiceInterface service = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(service);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass = {}", service.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();

    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 cglib를 사용하고 클래스기반 프록시 사용")
    void proxyTargetClass() {
        ServiceInterface service = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(service);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass = {}", service.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();

    }

    @Test
    @DisplayName("구체 클래스만 있으면 cglib 동적프록시 사용")
    void concreteClassProxy() {
        ConcreteService service = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(service);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("targetClass = {}", service.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();

    }
}
