package com.example.spring.db.repository;

import com.example.spring.db.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j // jdbc Connection 을 parameter 로 넘김
@RequiredArgsConstructor
public class MemberRepositoryV2 {

    public Member save(Connection conn, Member member) {
        String sql = "insert into member(member_id, money) values (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();

            return member;
        } catch (SQLException e) {
            log.error("exception occur ", e);
            throw new RuntimeException(e);
        }
    }

    public Member findById(Connection conn, String memberId) {
        String sql = "select * from member where member_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member(rs.getString("member_id"), rs.getInt("money"));
                JdbcUtils.closeResultSet(rs);
                return member;
            } else {
                JdbcUtils.closeResultSet(rs);
                throw new NoSuchElementException("member is not found");
            }
        } catch (SQLException e) {
            log.error("exception occur ", e);
            throw new RuntimeException(e);
        }
    }

    public int update(Connection conn, String memberId, int money) {
        String sql = "update member set money = ? where member_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("exception occur ", e);
            throw new RuntimeException(e);
        }
    }

    public int delete(Connection conn, String memberId) {
        String sql = "delete from member  where member_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("exception occur ", e);
            throw new RuntimeException(e);
        }
    }

}
