package com.example.spring.db.repository;

import com.example.spring.db.domain.Member;
import com.example.spring.db.repository.ex.MyDbException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

// 이미 해결되어있지만 throws SQLException 예외 누수 문제 해결 버전. 서비스 레이어에서 throws SQLException을 체크하지 않는다.
@Slf4j // transaction manager & DataSourceUtils
@RequiredArgsConstructor
@Repository
public class MemberRepositoryV4_1 implements MemberRepository {

    private final DataSource dataSource;

    @Override
    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection conn = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();

            return member;
        } catch (SQLException e) {
            log.error("exception occur ", e);
            // throw e;
            throw new MyDbException(e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

    @Override
    public Member findById(String memberId) {
        String sql = "select * from member where member_id = ?";

        Connection conn = DataSourceUtils.getConnection(dataSource);
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
            // throw e;
            throw new MyDbException(e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

    @Override
    public int update(String memberId, int money) {
        String sql = "update member set money = ? where member_id = ?";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("exception occur ", e);
            // throw e;
            throw new MyDbException(e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

    @Override
    public int delete(String memberId) {
        String sql = "delete from member  where member_id = ?";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("exception occur ", e);
            // throw e;
            throw new MyDbException(e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

}
