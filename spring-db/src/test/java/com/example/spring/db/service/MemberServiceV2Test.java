package com.example.spring.db.service;

import com.example.spring.db.domain.Member;
import com.example.spring.db.repository.MemberRepositoryV1;
import com.example.spring.db.repository.MemberRepositoryV2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import java.sql.SQLException;

import static com.example.spring.db.connection.ConnectionConst.*;

// 트랜잭션, 커넥션을 넘겨서 동작
class MemberServiceV2Test {
    public static final String MemberA = "memberA";
    public static final String MemberB = "memberB";
    public static final String MemberEX = "ex";

    private MemberRepositoryV2 memberRepository;
    private MemberServiceV2 memberService;
    private DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

    @BeforeEach
    void before() {
//        DataSource dataSource;

        memberRepository = new MemberRepositoryV2();
        memberService = new MemberServiceV2(dataSource, memberRepository);
    }

    @AfterEach
    void after() throws SQLException {
        // 좀 구리지만 하나씩 다 지워준다.
        memberRepository.delete(dataSource.getConnection(), MemberA);
        memberRepository.delete(dataSource.getConnection(), MemberB);
        memberRepository.delete(dataSource.getConnection(), MemberEX);
    }

    @Test
    void accountTransfer() throws SQLException {
        // given
        Member memberA = new Member(MemberA, 10000);
        Member memberB = new Member(MemberB, 10000);
        memberRepository.save(dataSource.getConnection(),  memberA);
        memberRepository.save(dataSource.getConnection(), memberB);

        // when
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

        // then
        Member byIdA = memberRepository.findById(dataSource.getConnection(), memberA.getMemberId());
        Member byIdB = memberRepository.findById(dataSource.getConnection(), memberB.getMemberId());

        Assertions.assertThat(byIdA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(byIdB.getMoney()).isEqualTo(12000);
    }

    @Test
    void accountTransferEX() throws SQLException {
        // given
        Member memberA = new Member(MemberA, 10000);
        Member memberEX = new Member(MemberEX, 10000);
        memberRepository.save(dataSource.getConnection(), memberA);
        memberRepository.save(dataSource.getConnection(), memberEX);

        // when
        Assertions.assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberId(), memberEX.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);

        // then
        Member byIdA = memberRepository.findById(dataSource.getConnection(), memberA.getMemberId());
        Member byIdB = memberRepository.findById(dataSource.getConnection(), memberEX.getMemberId());

        Assertions.assertThat(byIdA.getMoney()).isEqualTo(10000);
        Assertions.assertThat(byIdB.getMoney()).isEqualTo(10000);
    }

}