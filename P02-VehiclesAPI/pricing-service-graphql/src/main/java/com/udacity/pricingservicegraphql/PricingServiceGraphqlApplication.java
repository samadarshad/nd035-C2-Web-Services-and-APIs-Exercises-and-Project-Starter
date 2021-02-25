package com.udacity.pricingservicegraphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PricingServiceGraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceGraphqlApplication.class, args);
    }

}
