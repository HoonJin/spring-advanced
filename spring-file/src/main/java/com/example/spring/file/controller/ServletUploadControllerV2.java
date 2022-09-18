package com.example.spring.file.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
public class ServletUploadControllerV2 {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/servlet/v2/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/servlet/v2/upload")
    public String saveFileV2(HttpServletRequest request) throws ServletException, IOException {
        log.info("req = {}", request);

        String itemName = request.getParameter("itemName");
        log.info("itemName={}", itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts = {}", parts);

        parts.forEach(part -> {
            log.info("partname={}", part.getName());

            part.getHeaderNames().forEach(h -> log.info("part header {} = {}", h, part.getHeader(h)));

            String submittedFileName = part.getSubmittedFileName();
            log.info("submittedFileName = " + submittedFileName);
            log.info("size = {}", part.getSize());

            try(InputStream inputStream = part.getInputStream()) {
                String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
                log.info("body = {}", body);

                if (StringUtils.hasText(part.getSubmittedFileName())) {
                    String filePath = fileDir + part.getSubmittedFileName();
                    part.write(filePath);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        return "upload-form";
    }
}
