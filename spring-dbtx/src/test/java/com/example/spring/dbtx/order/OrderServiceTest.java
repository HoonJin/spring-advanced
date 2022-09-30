package com.example.spring.dbtx.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void order() throws NotEnoughMoneyException {
        Order order = new Order();
        order.setUsername("normal");

        orderService.order(order);

        Order findOrder = orderRepository.findById(order.getId()).get();
        Assertions.assertThat(findOrder.getPayStatus()).isEqualTo("completed");
    }

    @Test
    void order_with_RuntimeException() {
        Order order = new Order();
        order.setUsername("exception");

        Assertions.assertThatThrownBy(() -> orderService.order(order))
                .isInstanceOf(RuntimeException.class);

        Optional<Order> orderOptional = orderRepository.findById(order.getId());
        Assertions.assertThat(orderOptional).isEmpty();

    }

    @Test
    void order_with_BizException() {
        Order order = new Order();
        order.setUsername("not enough");

        try {
            orderService.order(order);
        } catch (NotEnoughMoneyException e) {
            log.info("별도 처리 필요 안내");
        }

        Order findOrder = orderRepository.findById(order.getId()).get();
        Assertions.assertThat(findOrder.getPayStatus()).isEqualTo("waiting");

    }

}