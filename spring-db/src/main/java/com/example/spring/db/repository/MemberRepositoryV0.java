package com.example.spring.db.repository;

import com.example.spring.db.connection.DBConnectionUtil;
import com.example.spring.db.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j // jdbc DriverManager 사용
public class MemberRepositoryV0 {

    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values (?, ?)";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();

            return member;
        } catch (SQLException e) {
            log.error("exception occur ", e);
            throw new RuntimeException(e);
        }
    }

    public Member findById(String memberId) {
        String sql = "select * from member where member_id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member(rs.getString("member_id"), rs.getInt("money"));
                rs.close();
                return member;
            } else {
                rs.close();
                throw new NoSuchElementException("member is not found");
            }
        } catch (SQLException e) {
            log.error("exception occur ", e);
            throw new RuntimeException(e);
        }
    }

    public int update(String memberId, int money) {
        String sql = "update member set money = ? where member_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("exception occur ", e);
            throw new RuntimeException(e);
        }
    }

    public int delete(String memberId) {
        String sql = "delete from member  where member_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, memberId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("exception occur ", e);
            throw new RuntimeException(e);
        }
    }

}
