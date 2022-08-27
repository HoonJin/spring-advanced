package com.example.spring.db.translator;

import com.example.spring.db.domain.Member;
import com.example.spring.db.repository.ex.MyDbException;
import com.example.spring.db.repository.ex.MyDuplicateKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import static com.example.spring.db.connection.ConnectionConst.*;

public class ExTranslatorV1Test {

    private final DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    Repository repository;
    Service service;

    @BeforeEach
    void init() {
        repository = new Repository(dataSource);
        service = new Service(repository);
    }

    @Test
    void duplicateKeySave() {
        service.create("id");
        service.create("id");
    }

    @Slf4j
    @RequiredArgsConstructor
    static class Service {
        private final Repository repository;

        public void create(String memberId) {
            try {
                repository.save(new Member(memberId, 0));
            } catch (MyDuplicateKeyException e) {
                log.error("키 중복으로 인한 복구시도");
                String retryId = generateNewId(memberId);
                log.info("retryId = {}", retryId);
                repository.save(new Member(retryId, 0));
            }
        }

        private String generateNewId(String memberId) {
            return memberId + UUID.randomUUID().toString().substring(0, 3);
        }
    }

    @RequiredArgsConstructor
    static class Repository {
        private final DataSource dataSource;

        public Member save(Member member) {
            String sql = "insert into member(member_id, money) values (?, ?);";

            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                conn = dataSource.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, member.getMemberId());
                pstmt.setInt(2, member.getMoney());
                pstmt.executeUpdate();
                return member;
            } catch (SQLException e) {
                if (e.getErrorCode() == 23505) { // h2 db의 data duplicate error code
                    throw new MyDuplicateKeyException(e);
                } else {
                    throw new MyDbException(e);
                }
            } finally {
                JdbcUtils.closeStatement(pstmt);
                JdbcUtils.closeConnection(conn);
            }
        }
    }
}
