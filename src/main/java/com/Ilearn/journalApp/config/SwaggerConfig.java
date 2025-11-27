package com.Ilearn.journalApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Journal App APIs")
                        .description("By Soubhagya"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local environment"),
                        new Server()
                                .url("https://your-prod-url.com")
                                .description("Production environment")
                ))
                .tags(List.of(
                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("Public APIs")
                                .description("Endpoints open without authentication"),

                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("User APIs")
                                .description("User operations"),

                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("Journal APIs")
                                .description("Journal entry operations"),

                        new io.swagger.v3.oas.models.tags.Tag()
                                .name("Admin APIs")
                                .description("Admin-only operations")
                ))
                // Add bearer JWT security globally
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));
    }
}
