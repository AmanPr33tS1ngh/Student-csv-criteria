package com.csvdatanatwest.natwestcsvdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("NatWest CSV Data API")
                        .version("1.0")
                        .description("API documentation for NatWest Internship Project")
                        .contact(new Contact()
                                .name("Amanpreet Singh")
                                .email("amanpreet.formal@gmail.com")));
    }
}
