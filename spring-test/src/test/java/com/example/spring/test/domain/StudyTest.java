package com.example.spring.test.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class StudyTest {

    @Test
    void create_new_study() {
        Study study = new Study(10);
        assertNotNull(study);

        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태가 DRAFT여야 한다");
        assertTrue(study.getLimit() > 0, () -> "스터디 참석 가능인원은 0보다 커야한다.");
    }

    @Test
    @DisplayName("일부러 실패하는 케이스")
    void create_new_study2() {
        Study study = new Study();
        study.setStatus(StudyStatus.STARTED);
        study.setLimit(-1);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태가 DRAFT여야 한다"),
                () -> assertTrue(study.getLimit() > 0, "스터디 참석 가능인원은 0보다 커야한다.")
        );
    }

    @Test
    void create_new_study3() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals(ex.getMessage(), "limit greater then 0");
    }

    @Test
    @EnabledOnOs({OS.WINDOWS})
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL") // 내부코드와 동일한 어노테이션
    void create_new_study4() {
        assumeTrue("LOCAL".equals(System.getenv("TEST_ENV")));
    }

}