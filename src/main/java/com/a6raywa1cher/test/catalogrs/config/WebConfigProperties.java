package com.a6raywa1cher.test.catalogrs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "web")
public class WebConfigProperties {
    private List<String> corsAllowedOrigins = new ArrayList<>();
}
