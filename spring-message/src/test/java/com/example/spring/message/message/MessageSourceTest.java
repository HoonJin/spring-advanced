package com.example.spring.message.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void helloMessage() {
        String result = messageSource.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> messageSource.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = messageSource.getMessage("no_code", null, "기본메시지", null);
        assertThat(result).isEqualTo("기본메시지");
    }

    @Test
    void argumentMessage() {
        String result = messageSource.getMessage("hello.name", new String[]{"SPRING"}, null);
        assertThat(result).isEqualTo("안녕 SPRING");
    }

    @Test
    void defaultLang() {
        String result = messageSource.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");

        String result1 = messageSource.getMessage("hello", null, Locale.KOREA);
        assertThat(result1).isEqualTo("안녕");
    }

    @Test
    void enLang() {
        String result = messageSource.getMessage("hello", null, Locale.ENGLISH);
        assertThat(result).isEqualTo("hello");
    }
}
