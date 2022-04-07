package com.example.spring.proxy.trace.logtrace;

import com.example.spring.proxy.trace.TraceId;
import com.example.spring.proxy.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private TraceId traceIdHolder;

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        long start = System.currentTimeMillis();
        log.info("[{}] {}{}", traceIdHolder.getId(), addSpace(START_PREFIX, traceIdHolder.getLevel()), message);
        return new TraceStatus(traceIdHolder, start, message);
    }

    private void syncTraceId() {
        if (traceIdHolder != null) {
            traceIdHolder = traceIdHolder.createNextId();
        } else {
            traceIdHolder = new TraceId();
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private String addSpace(String prefix, int level) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < level; i++) {
            String s = (i == level - 1) ? "|" + prefix : "|  ";
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }

    private void complete(TraceStatus status, Exception e) {
        long stop = System.currentTimeMillis();
        long duration = stop - status.getStartTimeMs();

        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), duration);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), duration, e.toString());
        }
        releaseTraceId();
    }

    private void releaseTraceId() {
        if (traceIdHolder.isFirstLevel()) {
            traceIdHolder = null;
        } else {
            traceIdHolder = traceIdHolder.createPrevId();
        }

    }
}
