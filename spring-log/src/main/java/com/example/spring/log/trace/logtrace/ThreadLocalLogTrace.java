package com.example.spring.log.trace.logtrace;

import com.example.spring.log.trace.TraceId;
import com.example.spring.log.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private final ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        long start = System.currentTimeMillis();
        log.info("[{}] {}{}", traceIdHolder.get().getId(), addSpace(START_PREFIX, traceIdHolder.get().getLevel()), message);
        return new TraceStatus(traceIdHolder.get(), start, message);
    }

    private void syncTraceId() {
        if (traceIdHolder.get() != null) {
            traceIdHolder.set(traceIdHolder.get().createNextId());
        } else {
            traceIdHolder.set(new TraceId());
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
        if (traceIdHolder.get().isFirstLevel()) {
            traceIdHolder.remove();
        } else {
            traceIdHolder.set(traceIdHolder.get().createPrevId());
        }

    }
}
