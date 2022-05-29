package com.example.spring.test.study;

import com.example.spring.test.domain.Study;
import com.example.spring.test.domain.StudyStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class StudyTest {

    @Test
    void create_new_study() {
        Study study = new Study(10);
        assertNotNull(study);

        Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태가 DRAFT여야 한다");
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


    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, 'java'", "20, 'python'"})
    void parameterizedTest1(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println("study = " + study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0));
        }
    }

}