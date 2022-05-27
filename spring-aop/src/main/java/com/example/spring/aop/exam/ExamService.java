package com.example.spring.aop.exam;

import com.example.spring.aop.exam.annotation.Trace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExampleRepository exampleRepository;

    @Trace
    public void request(String itemId) {
        exampleRepository.save(itemId);
    }
}
