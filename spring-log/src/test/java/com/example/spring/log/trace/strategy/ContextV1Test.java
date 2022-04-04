package com.example.spring.log.trace.strategy;

import com.example.spring.log.trace.strategy.code.ContextV1;
import com.example.spring.log.trace.strategy.code.Strategy;
import com.example.spring.log.trace.strategy.code.StrategyLogic1;
import com.example.spring.log.trace.strategy.code.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {
    @Test
    void strategyMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long start = System.currentTimeMillis();
        log.info("logic1 started");
        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("end duration = {}", duration);
    }

    private void logic2() {
        long start = System.currentTimeMillis();
        log.info("logic2 started");
        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("end duration = {}", duration);
    }

    @Test
    void strategyV1() {
        Strategy strategy1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategy1);
        context1.execute();

        Strategy strategy2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategy2);
        context2.execute();
    }

    @Test
    void strategyV2() {
        Strategy strategy = new Strategy() {
            @Override
            public void call() {
                log.info("logic temp started");
            }
        };

        ContextV1 contextV1 = new ContextV1(strategy);
        log.info("strategy = {}", strategy);
        contextV1.execute();
    }

    @Test
    void strategyV3() {
        ContextV1 contextV1 = new ContextV1(() -> log.info("logic temp started"));
        contextV1.execute();
    }

}
