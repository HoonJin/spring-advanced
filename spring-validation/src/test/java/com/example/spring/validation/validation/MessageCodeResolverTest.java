package com.example.spring.validation.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodeResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        Arrays.stream(messageCodes).forEach(code -> System.out.println("messageCode = " + code));
        assertThat(messageCodes).containsExactly(
                "required.item",
                "required"
        );
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        Arrays.stream(messageCodes).forEach(code -> System.out.println("messageCode = " + code));

        assertThat(messageCodes).containsExactly( // 우선순위는 자세한 것부터 덜 자세한 것으로 내려감
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }
}
