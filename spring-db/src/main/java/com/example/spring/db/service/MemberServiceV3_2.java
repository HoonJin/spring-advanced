package com.example.spring.db.service;

import com.example.spring.db.domain.Member;
import com.example.spring.db.repository.MemberRepositoryV3;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

// transaction template
public class MemberServiceV3_2 {

    private final TransactionTemplate transactionTemplate;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) {
        transactionTemplate.executeWithoutResult((status) -> {
            Member from = memberRepository.findById(fromId);
            Member to = memberRepository.findById(toId);

            memberRepository.update(from.getMemberId(), from.getMoney() - money);

            // 훼이크성 validation
            if (to.getMemberId().equals("ex"))
                throw new IllegalStateException("이체중 예외 발생");

            memberRepository.update(to.getMemberId(), to.getMoney() + money);
            // 비지니스 로직 종료
        });
    }
}
