package com.a6raywa1cher.test.catalogrs.integration;

import com.a6raywa1cher.test.catalogrs.exception.FileStorageOperationException;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

@Component
@Slf4j
public class MinioFileStorage implements FileStorage {
    private final MinioClient minioClient;
    private final String bucketName;
    private final Random random; // Instances of java.util.Random are threadsafe since Java 7

    @Autowired
    public MinioFileStorage(MinioClient minioClient, @Value("${storage.minio.bucket}") String bucketName, @Value("#{new java.util.Random()}") Random random) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
        this.random = random;
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
            String suggestedFileName = multipartFile.getName();
            String filePath = generatePath(folder, suggestedFileName);
            ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filePath)
                    .contentType(multipartFile.getContentType())
                    .stream(inputStream, fileSize, -1)
                    .build());
            return response.object();
        } catch (Exception e) {
            throw new FileStorageOperationException("upload", e);
        }
    }

    private String generatePath(String folder, String suggestedFileName) {
        int randomByte = random.nextInt(0xFF + 1); // we want two symbols for a subfolder name
        String subfolder = Integer.toHexString(randomByte);

        String extension = StringUtils.getFilenameExtension(suggestedFileName);
        String fileName = UUID.randomUUID() + (extension == null ? "" : "." + extension);

        return String.join("/", folder, subfolder, fileName);
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
