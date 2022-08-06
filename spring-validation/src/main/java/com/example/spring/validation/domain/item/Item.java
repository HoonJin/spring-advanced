package com.example.spring.validation.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
//@ScriptAssert(
//        lang = "javascript",
//        script = "_this.price * _this.quantity >= 10000",
//        message = "총합이 10,000원이 넘도록 해주세요."
//) // 실무에서 사용하기엔 단순하고 제약 조건이 많다.
public class Item {

//    @NotNull(groups = UpdateCheck.class)
    // groups 를 구현하는 것보다 save dto, update dto를 별개로 구성하는 것이 훨씬 낫다.
    private Long id;

//    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class}) // error.properties 에서 메시지를 읽어온다
    private String itemName;

//    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
//    @Range(min = 1000, max = 10000000)
    private Integer price;

//    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
//    @Max(value = 9999, groups = SaveCheck.class)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
