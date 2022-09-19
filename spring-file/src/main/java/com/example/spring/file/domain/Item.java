package com.example.spring.file.domain;

import java.util.List;

public record Item(
        Long id,
        String iteName,
        UploadFile attacheFile,
        List<UploadFile> imageFiles
) {
}
