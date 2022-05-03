package com.example.spring.test.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Study {

    private StudyStatus status = StudyStatus.DRAFT;

    private int limit;

    public Study() {}

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit greater then 0");
        }
        this.limit = limit;
    }

}
