package com.notification.system.swagger.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPiConfig {

    private final String Security_SCHEME_NAME="Bearer Authentication";

    @Bean
    public OpenAPI signalLoopOpenApi(){
        return new OpenAPI()
                .info(
                new Info()
                        .title("Notification System API")
                        .description("""
                                Backend API for an intelligent notification management system.
                                Features:
                                    JWT Authentication
                                    Notification Processing
                                    Kafka Messaging
                                    Redis Caching
                                    PostgreSQL
                                    Flyway
                                """)
                        .version("1.0.0")
                        .license(new License().name("MIT"))
                ).addSecurityItem(
                        new SecurityRequirement()
                                .addList(Security_SCHEME_NAME)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        Security_SCHEME_NAME,
                                        new SecurityScheme()
                                                .name(Security_SCHEME_NAME)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("jwt")
                                )
                );

    }
}
