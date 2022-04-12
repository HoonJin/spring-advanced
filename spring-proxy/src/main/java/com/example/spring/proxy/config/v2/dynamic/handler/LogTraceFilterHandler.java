package com.example.spring.proxy.config.v2.dynamic.handler;

import com.example.spring.proxy.trace.TraceStatus;
import com.example.spring.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace trace;
    private final String[] patterns;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!PatternMatchUtils.simpleMatch(patterns, method.getName()))
            return method.invoke(target, args);

        TraceStatus status = trace.begin(method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()");
        try {
            Object invoke = method.invoke(target, args);
            trace.end(status);
            return invoke;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
