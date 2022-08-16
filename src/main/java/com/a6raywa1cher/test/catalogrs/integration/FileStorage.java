package com.a6raywa1cher.test.catalogrs.integration;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    String upload(MultipartFile multipartFile, String folder);

    void delete(String id);
}
