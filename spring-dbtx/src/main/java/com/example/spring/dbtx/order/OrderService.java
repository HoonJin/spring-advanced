package com.example.spring.dbtx.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void order(Order order) throws NotEnoughMoneyException {
        log.info("call order");
        orderRepository.save(order);

        log.info("ordering is process");

        if (order.getUsername().equals("exception")) {
            log.info("occur sys exception");
            throw new RuntimeException("system exception");
        } else if (order.getUsername().equals("not enough")) {
            log.info("occur not enough exception");
            order.setPayStatus("waiting");
            throw new NotEnoughMoneyException("not enough");
        } {
            order.setPayStatus("completed");
        }
        log.info("completed order");
    }
}
