package com.example.spring.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(FIndSlowTestExtension.class)
class SpringTestApplicationTest {

    @Test
    // @Disabled
    void create() throws InterruptedException {
        Thread.sleep(1200L);
        SpringTestApplication app = new SpringTestApplication();
        assertNotNull(app);
    }

    @RepeatedTest(10)
    void repeatedTest(RepetitionInfo info) {
        System.out.println("repeat test " + info.getCurrentRepetition());
    }

    @ParameterizedTest
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있어요"})
    @EmptySource // 비어있는 문자열을 넣어줌
    @NullSource // null을 넣어줌
    void parameterizedTest(String message) {
        System.out.println("message = " + message);
    }



    @SlowTest
    void slowTest() {
        System.out.println("run fast test");
    }

    @FastTest
    void fastTest() {
        System.out.println("run slow test");
    }

    @Test
    void create1() {
        System.out.println("create once");
    }

    @BeforeAll
    static void createAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each\n");
    }
}