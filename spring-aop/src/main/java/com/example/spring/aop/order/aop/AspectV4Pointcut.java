package com.example.spring.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV4Pointcut {

    @Around("com.example.spring.aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    @Around("com.example.spring.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[tx] start {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[tx] commit {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[tx] rollback {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[tx] release {}", joinPoint.getSignature());
        }
    }

}
