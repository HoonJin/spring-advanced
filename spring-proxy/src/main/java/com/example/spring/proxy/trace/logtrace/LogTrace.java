package com.example.spring.proxy.trace.logtrace;

import com.example.spring.proxy.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
