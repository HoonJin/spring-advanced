package com.example.spring.validation.validation;

import com.example.spring.validation.domain.item.Item;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    @Test
    void beanValidation() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Item item = new Item();
        item.setItemName(" "); // 공백일 수 없습니다
        item.setPrice(999); // 1000에서 10000000 사이여야 합니다
        item.setQuantity(10000); // 9999 이하여야 합니다

        validator.validate(item).forEach(itemConstraintViolation -> {
            System.out.println("itemConstraintViolation = " + itemConstraintViolation);
            System.out.println("itemConstraintViolation.getMessage() = " + itemConstraintViolation.getMessage());
        });
    }
}
