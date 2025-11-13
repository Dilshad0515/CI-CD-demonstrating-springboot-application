package com.example.app.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration using springdoc.
 * Visit /swagger-ui.html or /swagger-ui/index.html after running the app.
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Combined API").version("0.1.0").description("API docs - replace with your description"));
    }
}
