package com.example.spring.oauth2.controller;

import com.example.spring.oauth2.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {

    @GetMapping("/api/users")
    public User users() {
        return new User("kim", 20);
    }
}
