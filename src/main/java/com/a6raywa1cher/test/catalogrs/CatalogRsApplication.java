package com.a6raywa1cher.test.catalogrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.a6raywa1cher.test.catalogrs")
public class CatalogRsApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CatalogRsApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CatalogRsApplication.class, args);
    }
}
