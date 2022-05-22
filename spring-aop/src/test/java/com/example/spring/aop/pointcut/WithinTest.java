package com.example.spring.aop.pointcut;

import com.example.spring.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method method;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        method = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinExact() {
        pointcut.setExpression("within(com.example.spring.aop.member.MemberServiceImpl)");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinStar() {
        pointcut.setExpression("within(com.example.spring.aop.member.*Service*)");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(com.example.spring.aop..*)");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test // execution 과 다르게 타입이 명확해야 한다.
    void withinSuperTypeFalse() {
        pointcut.setExpression("within(com.example.spring.aop.member.MemberService)");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void executionSuperTypeFalse() {
        pointcut.setExpression("execution(* com.example.spring.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }
}
