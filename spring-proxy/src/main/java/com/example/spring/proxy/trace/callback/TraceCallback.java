package com.example.spring.proxy.trace.callback;

public interface TraceCallback<T> {

    T call();
}
