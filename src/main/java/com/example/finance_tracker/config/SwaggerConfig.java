package com.example.finance_tracker.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        private static final String BEARER_AUTH = "Bearer Authentication";

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .addSecurityItem(createSecurityRequirement())
                                .components(createComponents());
        }

        private SecurityRequirement createSecurityRequirement() {
                return new SecurityRequirement()
                                .addList(BEARER_AUTH);
        }

        private Components createComponents() {
                return new Components()
                                .addSecuritySchemes(BEARER_AUTH, createSecurityScheme());
        }

        private SecurityScheme createSecurityScheme() {
                return new SecurityScheme()
                                .name(BEARER_AUTH)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT");
        }
}
