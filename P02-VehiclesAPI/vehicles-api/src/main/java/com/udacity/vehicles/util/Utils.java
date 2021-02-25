package com.udacity.vehicles.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.vehicles.util.graphqlclient.GraphqlClientMvc;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;


public class Utils {

    public static GraphqlClientMvc createGraphqlClientMvc(String endpoint, String serviceAlias, LoadBalancerClient loadBalancerClient) {
        ServiceInstance serviceInstance=loadBalancerClient.choose(serviceAlias);

        System.out.println(serviceInstance.getUri());

        String baseUrl=serviceInstance.getUri().toString();

        return new GraphqlClientMvc(
                new RestTemplateBuilder()
                        .rootUri(baseUrl + endpoint)
                        .build(),
                new ObjectMapper());
    }

    public WebClient createWebClientLoadBalanced(String endpoint, ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        return WebClient
                .builder()
                .filter(lbFunction)
                .baseUrl(endpoint)
                .build();
    }
}
