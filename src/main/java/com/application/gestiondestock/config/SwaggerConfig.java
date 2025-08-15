package com.application.gestiondestock.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .info(new Info()
            .title("Gestion de stock REST API")
            .description("Documentation pour l'API REST Gestion des stocks")
            .version("1.0.0")
            .contact(new Contact()
                .name("Wiame Jaoui")
                .email("wiamejaoui.wj@gmail.com")
                .url("https://github.com/wiamjaoui"))
            .license(new License()
                .name("Apache 2.0")
                .url("https://apache.org/licenses/LICENSE-2.0")))
        .servers(List.of(
            new Server().url("http://localhost:8081").description("Développement local"),
            new Server().url("https://api-preprod.example.com").description("Pré-production"),
            new Server().url("https://api.example.com").description("Production")
        ))
        .components(new Components()
            .addSecuritySchemes("bearerAuth", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
            )
        )
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
  }
}