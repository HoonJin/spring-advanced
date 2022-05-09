package com.example.spring.proxy.config.v3.advice;

import com.example.spring.proxy.trace.TraceStatus;
import com.example.spring.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
public class LogTraceAdvice implements MethodInterceptor {

    private final LogTrace trace;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        TraceStatus status = trace.begin(method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()");
        try {
            Object invoke = invocation.proceed();
            trace.end(status);
            return invoke;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
