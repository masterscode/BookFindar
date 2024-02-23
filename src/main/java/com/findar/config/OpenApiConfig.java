package com.findar.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        //Meta data for documentation can be externalized
        final String securitySchemeName = "bearerAuth";
        final String apiDescription = "Implementation of a RESTful API for a simple online bookstore";
        final Contact apiContact = new Contact()
                .name("Emmanuel Ogbinaka")
                .url("https://github.com/masterscode/BookFindar")
                .email("ogbinakaemmanuel@gmail.com");
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title("Book Store")
                        .description(apiDescription)
                        .termsOfService("Terms of service")
                        .contact(apiContact)
                        .version("v1")
                );
    }
}
