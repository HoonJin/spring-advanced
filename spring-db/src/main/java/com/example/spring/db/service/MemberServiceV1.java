package com.example.spring.db.service;

import com.example.spring.db.domain.Member;
import com.example.spring.db.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) {
        Member from = memberRepository.findById(fromId);
        Member to = memberRepository.findById(toId);

        memberRepository.update(from.getMemberId(), from.getMoney() - money);

        // 훼이크성 validation
        if (to.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }

        memberRepository.update(to.getMemberId(), to.getMoney() + money);
    }
}
