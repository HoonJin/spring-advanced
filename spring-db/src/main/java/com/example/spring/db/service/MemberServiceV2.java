package com.example.spring.db.service;

import com.example.spring.db.domain.Member;
import com.example.spring.db.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection conn = dataSource.getConnection();
        try {
            // 오토커밋 비활성화함으로써 트랜잭션 시작
            conn.setAutoCommit(false);

            // 비지니스 로직 시작
            Member from = memberRepository.findById(conn, fromId);
            Member to = memberRepository.findById(conn, toId);

            memberRepository.update(conn, from.getMemberId(), from.getMoney() - money);

            // 훼이크성 validation
            if (to.getMemberId().equals("ex")) {
                throw new IllegalStateException("이체중 예외 발생");
            }

            memberRepository.update(conn, to.getMemberId(), to.getMoney() + money);
            // 비지니스 로직 종료

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            // auto commit 을 다시 바꿔서 돌려줘야 한다.
            conn.setAutoCommit(true);
            // connection pool을 쓸 때는 close 가 커넥션 종료가 아닌 pool에 반납
            conn.close();
        }
    }
}
