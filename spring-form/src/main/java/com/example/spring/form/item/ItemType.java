package com.example.spring.form.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ItemType {

    BOOK("도서"), FOOD("음식"), ETC("기타");

    private final String description;

}
