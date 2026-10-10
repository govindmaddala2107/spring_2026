package com.gomad.complete_tutorial.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // this tells Swagger UI that we want to make use of JWT tokens which will be passed in HTTP authorization header.
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT Bearer Token");
        String tokenList = "Bearer Authentication";
        SecurityRequirement bearerRequirement = new SecurityRequirement()
                .addList(tokenList);

        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Title")
                        .version("6.0")
                        .description("This is a spring boot tutorials description.")
                        .license(new License().name("GoMaD 2.0").url("https://www.gomad.com"))
                        .contact(new Contact().name("govind").email("govind@gmail.com").url("https://www.govind.com"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("https://www.externaldocx.com")
                )
                .components(new Components()
                        .addSecuritySchemes(tokenList, bearerScheme))
                .addSecurityItem(bearerRequirement);
    }
}
