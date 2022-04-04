package com.example.spring.log.trace.template;

import com.example.spring.log.trace.template.code.AbstractTemplate;
import com.example.spring.log.trace.template.code.SubClassLogic1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
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
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();
        AbstractTemplate template2 = new SubClassLogic1();
        template2.execute();
    }

    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("logic1 started");
            }
        };
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("logic2 started");
            }
        };
        template2.execute();
    }
}
