package com.example.spring.db.translator;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootTest
@Slf4j
public class SpringExceptionTranslatorTest {

    @Autowired
    DataSource dataSource;

    @Test
    void sqlExceptionErrorCode() throws SQLException {
        String sql = "select a, bad grammar";

        Connection connection = dataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            log.error("expected error: ", e);
            int errorCode = e.getErrorCode();
            Assertions.assertThat(errorCode).isEqualTo(42122);
        }
    }

    @Test
    void exceptionTranslator() throws SQLException {
        String sql = "select a, bad grammar";

        Connection connection = dataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            Assertions.assertThat(e.getErrorCode()).isEqualTo(42122);

            // sql-error-codes.xml 여기서 가져온다.
            SQLErrorCodeSQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator();
            DataAccessException exceptionTranslator = translator.translate("exceptionTranslator", sql, e);
            log.info("result exception: ", exceptionTranslator);

            Assertions.assertThat(exceptionTranslator.getClass()).isEqualTo(BadSqlGrammarException.class);
        }
    }
}
