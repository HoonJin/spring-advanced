package com.example.spring.exception.api;

import com.example.spring.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionController {

    record MemberDto(String memberId, String name) {};

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex"))
            throw new RuntimeException("ex");

        if (id.equals("bad"))
            throw new IllegalArgumentException("wrong argument");

        if (id.equals("user-ex"))
            throw new UserException("사용자 오류");

        return new MemberDto(id, "hello" + id);
    }
}
