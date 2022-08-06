package com.example.spring.validation.web.validation;

import com.example.spring.validation.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ValidationItemApiController {

    @PostMapping("/validation/api/items/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {
        log.info("rest controller addItem");

        if (bindingResult.hasErrors()) {
            log.error("error {}", bindingResult);
            return bindingResult.getAllErrors();
        }

        return form;
    }
}
