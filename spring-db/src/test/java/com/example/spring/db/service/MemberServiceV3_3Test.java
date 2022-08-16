
package com.example.spring.db.service;

import com.example.spring.db.domain.Member;
import com.example.spring.db.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static com.example.spring.db.connection.ConnectionConst.*;

@SpringBootTest // spring aop 를 적용하기 위해선 당연히 SpringBootTest 어노테이션을 사용해야 한다.
@Slf4j
class MemberServiceV3_3Test {

    public static final String MemberA = "memberA";
    public static final String MemberB = "memberB";
    public static final String MemberEX = "ex";

    @Autowired
    private MemberRepositoryV3 memberRepository;

    @Autowired
    private MemberServiceV3_3 memberService;

    @TestConfiguration
    static class TestConfig {
        @Bean // 디비 정보 configration이 없어서 수동으로 넣어주어야 한다.
        DataSource dataSource() {
            return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        }

//        @Bean
//        PlatformTransactionManager transactionManager() {
//            return new DataSourceTransactionManager(dataSource());
//        }
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
    void aopCheck() {
        log.info("memberRepository = {}", memberRepository.getClass());
        log.info("memberService = {}", memberService.getClass());

        Assertions.assertThat(AopUtils.isAopProxy(memberRepository)).isTrue();
        Assertions.assertThat(AopUtils.isAopProxy(memberService)).isTrue();
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