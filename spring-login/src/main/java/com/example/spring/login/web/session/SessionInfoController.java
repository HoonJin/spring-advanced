package com.example.spring.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return "not found session";

        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session key={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("getCreationTime={}", session.getCreationTime());
        log.info("getLastAccessedTime={}", session.getLastAccessedTime());
        log.info("isNew={}", session.isNew());
        return "session";
    }
}
