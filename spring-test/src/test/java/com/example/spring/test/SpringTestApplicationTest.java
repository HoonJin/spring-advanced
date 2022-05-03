package com.example.spring.test;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SpringTestApplicationTest {

    @Test
    // @Disabled
    void create() {
        SpringTestApplication app = new SpringTestApplication();
        assertNotNull(app);
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
        System.out.println("after each");
    }
}