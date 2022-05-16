package com.example.spring.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    @Pointcut("execution(* com.example.spring.aop.order..*(..))")
    private void allOrder() {
        // pointcut signature
    }

    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() {
    }

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    @Around("allOrder() && allService()")
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
