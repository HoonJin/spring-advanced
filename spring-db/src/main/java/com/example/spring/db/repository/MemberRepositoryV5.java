package com.example.spring.db.repository;

import com.example.spring.db.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Slf4j
@Repository
// jdbc template version
public class MemberRepositoryV5 implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryV5(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values (?, ?)";
        jdbcTemplate.update(sql, member.getMemberId(), member.getMoney());
        return member;
    }

    @Override
    public Member findById(String memberId) {
        String sql = "select * from member where member_id = ?";
        return jdbcTemplate.queryForObject(sql, memberRowMapper(), memberId);
    }

    private RowMapper<Member> memberRowMapper() {
//        new RowMapper<Member>() {
//            @Override
//            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return null;
//            }
//        }
        return ((rs, rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getString("member_id"));
            member.setMoney(rs.getInt("money"));
            return member;
        });
    }

    @Override
    public int update(String memberId, int money) {
        String sql = "update member set money = ? where member_id = ?";
        return jdbcTemplate.update(sql, money, memberId);
    }

    @Override
    public int delete(String memberId) {
        String sql = "delete from member  where member_id = ?";
        return jdbcTemplate.update(sql, memberId);
    }

}
