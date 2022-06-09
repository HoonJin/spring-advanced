package com.example.spring.validation.web.validation.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemUpdateForm {

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 10000000)
    private Integer price;

    @Range(max = 99999)
    private Integer quantity;
}
