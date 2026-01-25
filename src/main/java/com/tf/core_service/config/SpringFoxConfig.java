package com.tf.core_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringFoxConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("TF API")
                        .description("Transformer Api's")
                        .version("1.0"));
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
