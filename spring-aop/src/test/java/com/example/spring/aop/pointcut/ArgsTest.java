package com.example.spring.aop.pointcut;

import com.example.spring.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsTest {

    Method method;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        method = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    @Test
    void args() {
        assertThat(pointcut("args(String)")
                .matches(method, MemberServiceImpl.class)
        ).isTrue();

        assertThat(pointcut("args(Object)")
                .matches(method, MemberServiceImpl.class)
        ).isTrue();

        assertThat(pointcut("args()")
                .matches(method, MemberServiceImpl.class)
        ).isFalse();

        assertThat(pointcut("args(..)")
                .matches(method, MemberServiceImpl.class)
        ).isTrue();

        assertThat(pointcut("args(*)")
                .matches(method, MemberServiceImpl.class)
        ).isTrue();

        assertThat(pointcut("args(String, ..)")
                .matches(method, MemberServiceImpl.class)
        ).isTrue();
    }

    @Test
    void argsVsExecution() {
        assertThat(pointcut("args(String)")
                .matches(method, MemberServiceImpl.class)
        ).isTrue();

        assertThat(pointcut("execution(* *(String))")
                .matches(method, MemberServiceImpl.class)
        ).isTrue();

        assertThat(pointcut("args(java.io.Serializable)")
                .matches(method, MemberServiceImpl.class)
        ).isTrue();

        // execution은 정확하게 매칭시켜야 함
        assertThat(pointcut("execution(* *(java.io.Serializable))")
                .matches(method, MemberServiceImpl.class)
        ).isFalse();

        assertThat(pointcut("args(Object)")
                .matches(method, MemberServiceImpl.class)
        ).isTrue();

        assertThat(pointcut("execution(* *(Object))")
                .matches(method, MemberServiceImpl.class)
        ).isFalse();
    }
}
