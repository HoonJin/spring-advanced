package com.example.spring.test;

import com.example.spring.test.domain.Study;

public interface StudyRepository {

    Study save(Study study);
}
