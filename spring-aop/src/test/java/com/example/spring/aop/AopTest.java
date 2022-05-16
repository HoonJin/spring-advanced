package com.example.spring.aop;

import com.example.spring.aop.order.OrderRepository;
import com.example.spring.aop.order.OrderService;
import com.example.spring.aop.order.aop.AspectV1;
import com.example.spring.aop.order.aop.AspectV2;
import com.example.spring.aop.order.aop.AspectV3;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Import(AspectV3.class)
class AopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void aopInfo() {
        log.info("isProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }

}