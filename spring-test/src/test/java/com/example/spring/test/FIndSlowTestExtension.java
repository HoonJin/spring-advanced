package com.example.spring.test;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import java.lang.reflect.Method;

public class FIndSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final String KEY = "START_TIME";
    private static final Long THRESHOLD = 1000L;

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        String testClass = context.getRequiredTestClass().getName();
        String methodName = context.getRequiredTestMethod().getName();
        Store store = context.getStore(ExtensionContext.Namespace.create(testClass, methodName));

        store.put(KEY, System.currentTimeMillis());

    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        String testClass = context.getRequiredTestClass().getName();
        Method testMethod = context.getRequiredTestMethod();
        SlowTest annotation = testMethod.getAnnotation(SlowTest.class);
        String methodName = testMethod.getName();

        Store store = context.getStore(ExtensionContext.Namespace.create(testClass, methodName));

        long startTime = store.remove(KEY, long.class);
        long duration = System.currentTimeMillis() - startTime;

        if (duration > THRESHOLD && annotation == null) {
            System.out.printf("Please consider mark method [%s] with @SlowTest. take %d ms", methodName, duration);
        }
    }
}
