package com.example.spring.db.service;

import com.example.spring.db.domain.Member;
import com.example.spring.db.repository.MemberRepositoryV3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static com.example.spring.db.connection.ConnectionConst.*;

class MemberServiceV3_1Test {


    public static final String MemberA = "memberA";
    public static final String MemberB = "memberB";
    public static final String MemberEX = "ex";

    private MemberRepositoryV3 memberRepository;
    private MemberServiceV3_1 memberService;
    private final DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

    @BeforeEach
    void before() {
        memberRepository = new MemberRepositoryV3(dataSource);
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        memberService = new MemberServiceV3_1(transactionManager, memberRepository);
    }

    @AfterEach
    void after() {
        // 좀 구리지만 하나씩 다 지워준다.
        memberRepository.delete(MemberA);
        memberRepository.delete(MemberB);
        memberRepository.delete(MemberEX);
    }

    @Test
    void accountTransfer() {
        // given
        Member memberA = new Member(MemberA, 10000);
        Member memberB = new Member(MemberB, 10000);
        memberRepository.save( memberA);
        memberRepository.save(memberB);

        // when
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

        // then
        Member byIdA = memberRepository.findById(memberA.getMemberId());
        Member byIdB = memberRepository.findById(memberB.getMemberId());

        Assertions.assertThat(byIdA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(byIdB.getMoney()).isEqualTo(12000);
    }

    @Test
    void accountTransferEX() {
        // given
        Member memberA = new Member(MemberA, 10000);
        Member memberEX = new Member(MemberEX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEX);

        // when
        Assertions.assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberId(), memberEX.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);

        // then
        Member byIdA = memberRepository.findById(memberA.getMemberId());
        Member byIdB = memberRepository.findById(memberEX.getMemberId());

        Assertions.assertThat(byIdA.getMoney()).isEqualTo(10000);
        Assertions.assertThat(byIdB.getMoney()).isEqualTo(10000);
    }
}