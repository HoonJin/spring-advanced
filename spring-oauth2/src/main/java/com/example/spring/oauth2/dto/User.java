package com.example.spring.oauth2.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter(AccessLevel.NONE)
public class User {

    private String username;
    private int age;
}
