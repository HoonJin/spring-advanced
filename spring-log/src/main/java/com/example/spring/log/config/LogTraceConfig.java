package com.example.spring.log.config;

import com.example.spring.log.trace.logtrace.FieldLogTrace;
import com.example.spring.log.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }
}
