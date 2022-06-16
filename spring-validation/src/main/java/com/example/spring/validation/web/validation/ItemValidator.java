package com.example.spring.validation.web.validation;

import com.example.spring.validation.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    // errors = bindingResult's parent
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 10000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 10000000}, null );
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999)
            errors.rejectValue("quantity", "max", new Object[]{"9999"}, null);

        // 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, result}, "가격 * 수량의 합은 10,000 이 합니다. 현재: " + result);
            }
        }
    }
}
