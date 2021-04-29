package com.tplate.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEMA = "bearerAuth";
    private static final String SCHEMA = "bearer";
    private static final String BEARER_FORMAT = "JWT";
    private static final String MODULE_NAME = "Tplate API";
    private static final String API_VERSION = "v1";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEMA))
                .components(
                        new Components()
                                .addSecuritySchemes(SECURITY_SCHEMA,
                                        new SecurityScheme()
                                                .name(SECURITY_SCHEMA)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme(SCHEMA)
                                                .bearerFormat(BEARER_FORMAT)
                                )
                )
                .info(new Info().title(MODULE_NAME).version(API_VERSION));
    }
}