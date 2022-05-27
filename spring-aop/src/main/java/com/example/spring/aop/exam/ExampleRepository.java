package com.example.spring.aop.exam;

import com.example.spring.aop.exam.annotation.Retry;
import org.springframework.stereotype.Repository;

@Repository
public class ExampleRepository {

    private static int seq = 0;

    @Retry
    public String save(String itemId) {
        seq++;
        if (seq % 5 == 0) {
            throw new IllegalArgumentException("custom exception");
        }
        return itemId;
    }
}
