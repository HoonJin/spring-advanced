package com.example.spring.aop.exam.aspect;

import com.example.spring.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object retry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {}", joinPoint.getSignature());
        Exception exception = null;
        for (int i = 0; i < retry.value(); i++) {
            try {
                return joinPoint.proceed();
            } catch (Exception e) {
                exception = e;
            }
        }
        throw exception;
    }
}
