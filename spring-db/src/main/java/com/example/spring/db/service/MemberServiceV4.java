package com.example.spring.db.service;

import com.example.spring.db.domain.Member;
import com.example.spring.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// @Transactional AOP
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceV4 {

    private final MemberRepository memberRepository;

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) {
        Member from = memberRepository.findById(fromId);
        Member to = memberRepository.findById(toId);

        memberRepository.update(from.getMemberId(), from.getMoney() - money);

        // 훼이크성 validation
        if (to.getMemberId().equals("ex"))
            throw new IllegalStateException("이체중 예외 발생");

        memberRepository.update(to.getMemberId(), to.getMoney() + money);
        // 비지니스 로직 종료
    }
}
