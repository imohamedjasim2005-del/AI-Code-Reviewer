package com.aicodereviewer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("AI Code Reviewer API")
                                .version("1.0")
                                .description(
                                        "Static code analysis platform built using Spring Boot"
                                )
                                .contact(
                                        new Contact()
                                                .name("Jasim Mohamed")
                                                .email("your-email@example.com")
                                )
                );
    }
}