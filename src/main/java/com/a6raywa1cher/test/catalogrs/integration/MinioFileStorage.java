package com.a6raywa1cher.test.catalogrs.integration;

import com.a6raywa1cher.test.catalogrs.exception.FileStorageOperationException;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
@Slf4j
public class MinioFileStorage implements FileStorage {
    private final MinioClient minioClient;
    private final String bucketName;

    @Autowired
    public MinioFileStorage(MinioClient minioClient, @Value("${storage.minio.bucket}") String bucketName) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeBucket() throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(bucketName)
                    .config("public")
                    .build());
        }
        log.info("MinioFileStorage buckets has been initialized");
    }

    @Override
    public String upload(MultipartFile multipartFile, String folder) {
        try {
            long fileSize = multipartFile.getSize();
            InputStream inputStream = multipartFile.getInputStream();
            ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(folder + "/" + UUID.randomUUID())
                    .contentType(multipartFile.getContentType())
                    .stream(inputStream, fileSize, -1)
                    .build());
            return response.object();
        } catch (Exception e) {
            throw new FileStorageOperationException("upload", e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(id)
                    .build());
        } catch (Exception e) {
            throw new FileStorageOperationException("delete", id, e);
        }
    }
}
