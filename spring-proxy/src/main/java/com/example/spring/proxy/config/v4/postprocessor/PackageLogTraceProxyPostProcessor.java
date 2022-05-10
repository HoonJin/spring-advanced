package com.example.spring.proxy.config.v4.postprocessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
@RequiredArgsConstructor
public class PackageLogTraceProxyPostProcessor implements BeanPostProcessor {

    private final String basePackage;

    private final Advisor advisor;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("beanName={}, bean={}", beanName, bean.getClass());

        String packageName = bean.getClass().getPackageName();

        if (!packageName.startsWith(basePackage)) {
            return bean;
        }

        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);

        log.info("create proxy: target={}, proxy={}", bean.getClass(), proxyFactory.getClass());

        return proxyFactory.getProxy();
    }
}
