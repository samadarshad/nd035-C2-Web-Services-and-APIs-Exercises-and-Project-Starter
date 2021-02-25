package com.udacity.vehicles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Vehicles API",
                "This API manages vehicles. Created for Udacity Java Web Developer Nanodegree.",
                "1.0",
                "http://www.udacity.com/tos",
                new Contact("Samad Arshad", "https://github.com/samadarshad", "https://www.linkedin.com/in/samadarshad/"),
                "License of API", "http://www.udacity.com/license", Collections.emptyList());
    }
}