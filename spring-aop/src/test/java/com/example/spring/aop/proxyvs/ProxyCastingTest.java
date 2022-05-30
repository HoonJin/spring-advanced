package com.example.spring.aop.proxyvs;

import com.example.spring.aop.member.MemberService;
import com.example.spring.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // jdk proxy

        MemberService proxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy = {}", proxy.getClass());

        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl proxyImpl = (MemberServiceImpl) proxyFactory.getProxy();
            log.info("proxyImpl = {}", proxyImpl.getClass());
        });
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // jdk proxy

        MemberService proxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy = {}", proxy.getClass());

        MemberServiceImpl proxyImpl = (MemberServiceImpl) proxyFactory.getProxy();
        log.info("proxyImpl = {}", proxyImpl.getClass());
    }
}
