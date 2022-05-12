package com.example.spring.proxy.config.v6.aspect;

import com.example.spring.proxy.trace.TraceStatus;
import com.example.spring.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LogTraceAspect {

    private final LogTrace trace;

    // 어노테이션과 메서드가 어드바이저
    @Around("execution(* com.example.spring.proxy.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        String message = joinPoint.getSignature().toShortString();
        TraceStatus status = trace.begin(message);
        try {
            Object invoke = joinPoint.proceed();
            trace.end(status);
            return invoke;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
