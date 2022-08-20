package com.example.spring.login.filter;

import com.example.spring.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] uncheckUris = {
            "/",
            "/members/add",
            "/login",
            "/logout",
            "/css/*"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();

        try {
            if (isLoginCheckPath(uri)) {
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.warn("미인증 사용자 요청");
                    httpResponse.sendRedirect("/login?redirectURL=" + uri);
                    return;
                }
            }
            chain.doFilter(request, response);

        } catch (Exception e) {
            throw e; // exception을 내 주어야 WAS 까지 에러가 전달된다.
        }
    }

    private boolean isLoginCheckPath(String path) {
        return !PatternMatchUtils.simpleMatch(uncheckUris, path);
    }
}
