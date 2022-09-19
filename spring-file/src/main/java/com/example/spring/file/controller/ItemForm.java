package com.example.spring.file.controller;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ItemForm(
        Long itemId,
        String itemName,
        List<MultipartFile> imageFiles,
        MultipartFile attachFile
) {
}
