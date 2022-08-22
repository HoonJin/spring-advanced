package com.example.spring.login.web.resolver;

import com.example.spring.login.domain.member.Member;
import com.example.spring.login.web.SessionConst;
import com.example.spring.login.web.resolver.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // annotation 없이 그냥 타입만 체크하면 무조건 로그인 필요하도록 할수 있을듯.
        boolean hasAnnotationMethod = parameter.hasParameterAnnotation(Login.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotationMethod && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("RUN argument resolver - resolveArgument");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if (session == null)
            return null;
        else {
            return session.getAttribute(SessionConst.LOGIN_MEMBER);
        }
    }
}
