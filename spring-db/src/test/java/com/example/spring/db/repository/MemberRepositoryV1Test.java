package com.example.spring.db.repository;

import com.example.spring.db.domain.Member;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static com.example.spring.db.connection.ConnectionConst.*;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 memberRepository;

    @BeforeEach
    void beforeEach() {
        // 기본 driver manager 처럼 항상 새로운 커넥션을 획득하도록 한다.
//        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        memberRepository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() {
        // Create
        Member member = new Member("memberV6", 10000);
        memberRepository.save(member);

        // Read
        Member findMember = memberRepository.findById(member.getMemberId());
        log.info("findMember = {}", findMember);
        log.info("findMember == member is false: {}", findMember == member); // 객체 레퍼런스 값
        log.info("findMember equals member is true: {}", findMember.equals(member)); // override 된 equals 비교

        Assertions.assertThat(findMember).isEqualTo(member);

        // Update
        memberRepository.update(member.getMemberId(), 20000);
        Member updateMember = memberRepository.findById(member.getMemberId());
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);

        // Delete
        memberRepository.delete(member.getMemberId());
        Assertions.assertThatThrownBy(() -> memberRepository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }

}
