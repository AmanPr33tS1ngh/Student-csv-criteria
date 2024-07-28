package com.csvdatanatwest.natwestcsvdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
      @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("NatWest - Student CSV")
                .description("Application for Student APIs")
                .version("v0.0.1").contact(new Contact().name("aman").email("amanpreet.formal@gmail.com"))
                .license(new License().name("Apache 2.0")));
    }
}
