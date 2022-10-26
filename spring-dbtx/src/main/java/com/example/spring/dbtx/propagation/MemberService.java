package com.example.spring.dbtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    @Transactional
    public void joinV1(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("=== member repository start ==");
        memberRepository.save(member);
        log.info("=== member repository end ==");

        log.info("=== log repository start ==");
        logRepository.save(logMessage);
        log.info("=== log repository end ==");
    }

    @Transactional
    public void joinV2(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("=== member repository start ==");
        memberRepository.save(member);
        log.info("=== member repository end ==");

        try {
            log.info("=== log repository start ==");
            logRepository.save(logMessage);
        } catch (RuntimeException e) {
            log.error("fail to save logMessage ", e);
        }
        log.info("=== log repository end ==");
    }
}
