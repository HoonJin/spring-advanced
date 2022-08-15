package com.example.spring.db.service;

import com.example.spring.db.domain.Member;
import com.example.spring.db.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

// transaction manager
@RequiredArgsConstructor
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // 비지니스 로직 시작
            Member from = memberRepository.findById(fromId);
            Member to = memberRepository.findById(toId);

            memberRepository.update(from.getMemberId(), from.getMoney() - money);

            // 훼이크성 validation
            if (to.getMemberId().equals("ex"))
                throw new IllegalStateException("이체중 예외 발생");

            memberRepository.update(to.getMemberId(), to.getMoney() + money);
            // 비지니스 로직 종료

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
