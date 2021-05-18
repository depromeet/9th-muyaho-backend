package com.depromeet.muyaho.api.config.swagger;

import com.depromeet.muyaho.api.config.resolver.MemberId;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components().addSecuritySchemes("Authorization", new SecurityScheme()
            .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER).name("Authorization")))
            .info(new Info()
                .title("Muyaho API Server")
                .version("v0.0.1")
                .description("Muyaho API Docs"));
    }

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(MemberId.class);
    }

}
