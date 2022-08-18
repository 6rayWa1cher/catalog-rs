package com.a6raywa1cher.test.catalogrs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {
    @Value("${app.version}")
    private String version;

    @Value("${app.api-endpoint:#{null}}")
    private String apiEndpoint;

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("catalog-rs")
                        .version(version)
                        .license(new License()
                                .name("MIT License")
                                .url("https://github.com/6rayWa1cher/catalog-rs/blob/master/LICENSE")
                        )
                );
        if (apiEndpoint != null) {
            openAPI = openAPI
                    .servers(List.of(new Server().url(apiEndpoint)));
        }
        return openAPI;
    }
}