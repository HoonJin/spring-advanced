package com.example.spring.validation.web.validation.form;

import com.example.spring.validation.domain.item.Item;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemSaveForm {

    // id는 필요 없다.

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 10000000)
    private Integer price;

    private Integer quantity;

    public Item toEntity() {
        return new Item(itemName, price, quantity);
    }
}
