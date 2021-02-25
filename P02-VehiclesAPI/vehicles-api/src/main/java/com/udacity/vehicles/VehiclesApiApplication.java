package com.udacity.vehicles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.vehicles.client.graphqlclient.GraphqlClientMvc;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Launches a Spring Boot application for the Vehicles API,
 * initializes the car manufacturers in the database,
 * and launches web clients to communicate with maps and pricing.
 */
@SpringBootApplication
//@EnableSwagger2
@EnableJpaAuditing
@EnableEurekaClient
public class VehiclesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehiclesApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Web Client for the maps (location) API
     * @param endpoint where to communicate for the maps API
     * @return created maps endpoint
     */
    @Bean(name="maps")
    public WebClient webClientMaps(@Value("${maps.endpoint}") String endpoint) {
        return WebClient.create(endpoint);
    }

    /**
     * Web Client for the pricing API
     * @param endpoint where to communicate for the pricing API
     * @return created pricing endpoint
     */
//    @Bean(name="pricing")
//    public WebClient webClientPricing(@Value("${pricing.endpoint}") String endpoint, LoadBalancerClient lbClient) {
//        return WebClient
//                .builder()
//                .filter(new LoadBalancerExchangeFilterFunction(lbClient))
//                .baseUrl(endpoint)
//                .build();
//    }

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @Bean(name="pricinggraphql")
    @LoadBalanced
    public GraphqlClientMvc graphqlClientMvc(@Value("${pricing.endpoint}") String endpoint, @Value("${pricing.alias}") String serviceAlias) {
        ServiceInstance serviceInstance=loadBalancerClient.choose(serviceAlias);

        System.out.println(serviceInstance.getUri());

        String baseUrl=serviceInstance.getUri().toString();

        return new GraphqlClientMvc(
                new RestTemplateBuilder()
                        .rootUri(baseUrl + endpoint)
                        .build(),
                new ObjectMapper());
        //        return new GraphqlClientMvc(
//                new RestTemplateBuilder()
//                        .rootUri(endpoint)
//                        .build(),
//                new ObjectMapper());
    }

}
