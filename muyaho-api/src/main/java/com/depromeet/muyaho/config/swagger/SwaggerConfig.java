package com.depromeet.muyaho.config.swagger;

import com.depromeet.muyaho.config.resolver.MemberId;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Muyaho API Server")
                .version("v0.0.1")
                .description("Muyaho API Docs"));
    }

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(MemberId.class);
    }

}
