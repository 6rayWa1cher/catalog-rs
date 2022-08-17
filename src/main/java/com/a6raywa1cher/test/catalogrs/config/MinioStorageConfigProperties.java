package com.a6raywa1cher.test.catalogrs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URL;

@Data
@Configuration
@ConfigurationProperties(prefix = "storage.minio")
public class MinioStorageConfigProperties {
    private URL endpoint;

    private String bucket;

    private String accessKey;

    private String secretKey;

    private URI publicEndpoint;
}
