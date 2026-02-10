package com.ecommerce.sleekselects.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("SleekSelects API")
                                .version("1.0")
                                .description("""
                SleekSelects is a RESTful e-commerce backend API designed to manage products, categories, carts, orders, users, and media assets in a secure and scalable way.

                The system provides authentication and authorization using JWT, supports full product and category management, image upload and download, shopping cart operations, and order processing. It is built with Spring Boot following a layered architecture and exposes well-structured endpoints intended for consumption by web and mobile clients.

                All secured endpoints require a valid Bearer token. Responses are standardized using a unified API response format to ensure consistency and ease of integration.
                """)
                                .contact(new Contact()
                                        .name("Ahmed Saeed")
                                        .email("ahmed.saeed.dev.eg@gmail.com")
                                        .url("https://github.com/AHMEDSAEEDGIT"))
                                .license(new License()
                                        .name("MIT License")
                                        .url("https://opensource.org/licenses/MIT"))
                        )
                        .servers(List.of(
                                new Server()
                                        .url("http://localhost:8181")
                                        .description("Local development server")
                        ))
                        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                        .components(new Components()
                                .addSecuritySchemes("bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                        );
        }

}
