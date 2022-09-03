package com.example.spring.exception.api;

import com.example.spring.exception.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiExceptionV2Controller {

    record ErrorResult(String code, String message) {}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExceptionHandler(IllegalArgumentException e) {
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExceptionHandler(UserException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResult("USER-EX", e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exceptionHandler(Exception e) {
        return new ErrorResult("EX", e.getMessage());
    }

    record MemberDto(String memberId, String name) {};

    @GetMapping("/api2/members/{id}")
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
