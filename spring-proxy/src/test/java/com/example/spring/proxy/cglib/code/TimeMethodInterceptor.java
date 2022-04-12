package com.example.spring.proxy.cglib.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@RequiredArgsConstructor
@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    private final Object target;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("run timeproxy {}.{}", method.getDeclaringClass().getSimpleName(), method.getName());
        long start = System.currentTimeMillis();

        Object result = methodProxy.invoke(target, objects);
        // Object result = method.invoke(target, objects);

        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("timeproxy end: {}ms", duration);
        return result;
    }
}
