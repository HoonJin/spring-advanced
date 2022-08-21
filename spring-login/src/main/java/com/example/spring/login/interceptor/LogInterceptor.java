package com.example.spring.login.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        if (handler instanceof HandlerMethod handlerMethod) {
        }

        request.setAttribute(LOG_ID, uuid);
        log.info("[{} {}] REQ {}", method, requestURI, uuid);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();

        if (request.getAttribute(LOG_ID) instanceof String uuid) {
            log.info("[{} {}] RES {}", method, requestURI, uuid);
        }

        if (ex != null) {
            log.error("error!! ", ex);
        }
    }
}
