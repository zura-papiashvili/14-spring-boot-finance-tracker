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
                                .addSecurityItem(createSecurityRequirement()) // Add security requirement for JWT
                                .components(createComponents()) // Define the security scheme
                                .info(new io.swagger.v3.oas.models.info.Info().title("Finance TrackerAPI Documentation")
                                                .version("1.0"));
        }

        // Create the security requirement for the JWT token
        private SecurityRequirement createSecurityRequirement() {
                return new SecurityRequirement().addList(BEARER_AUTH);
        }

        // Define the security scheme for JWT
        private Components createComponents() {
                return new Components().addSecuritySchemes(BEARER_AUTH, createSecurityScheme());
        }

        // Define the JWT security scheme
        private SecurityScheme createSecurityScheme() {
                return new SecurityScheme()
                                .name(BEARER_AUTH)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT");
        }

}
