package com.example.spring.file.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Controller
public class ServletUploadControllerV1 {

    @GetMapping("/servlet/v1/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/servlet/v1/upload")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
        log.info("req = {}", request);

        String itemName = request.getParameter("itemName");
        log.info("itemName={}", itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts = {}", parts);

        return "upload-form";
    }
}
