package com.example.spring.login.web.session;

import com.example.spring.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void createSessionTest() {
        MockHttpServletResponse response = new MockHttpServletResponse();

        Member member = new Member();
        sessionManager.createSession(member, response);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        Object session = sessionManager.getSession(request);

        Assertions.assertThat(session)
                .isEqualTo(member);

        // 세션 만료
        sessionManager.expireSession(request);
        Assertions.assertThat(sessionManager.getSession(request))
                .isNull();
    }

}