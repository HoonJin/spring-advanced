package com.example.spring.test.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Study {

    private StudyStatus status = StudyStatus.DRAFT;

    private int limit;

    private Member owner;

    public Study() {}

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit greater then 0");
        }
        this.limit = limit;
    }

}
