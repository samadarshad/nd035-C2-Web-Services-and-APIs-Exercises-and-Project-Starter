package com.udacity.pricingservicegraphql;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.udacity.pricingservicegraphql.client.FindAllPricesQuery;
import com.udacity.pricingservicegraphql.client.FindPricesByVehicleIdQuery;
import com.udacity.pricingservicegraphql.client.GenerateAndAssignPriceMutation;
import com.udacity.pricingservicegraphql.entity.Price;
import com.udacity.pricingservicegraphql.graphqlclient.GraphqlClientMvc;
import com.udacity.pricingservicegraphql.repository.PriceRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:ApolloGraphQlClientTests"})
class ApolloGraphQlClientTests {
    @LocalServerPort
    private int port;

    private static final RestTemplate restTemplate = new RestTemplate();
    private String url;

    @BeforeEach
    void beforeEach(@Autowired PriceRepository priceRepository) {
        url = "http://localhost:" + port + "/graphql";

        priceRepository.save(new Price(null, new BigDecimal(1500d), "USD", 1L));
        priceRepository.save(new Price(null, new BigDecimal(2000d), "USD", 2L));
        priceRepository.save(new Price(null, new BigDecimal(1000d), "USD", 3L));
        priceRepository.save(new Price(null, new BigDecimal(700d), "GBP", 3L));
    }

    @AfterEach
    void afterEach(@Autowired PriceRepository priceRepository) {
        priceRepository.deleteAll();
    }


    @Test
    public void FindAllPrices() throws IOException {
        GraphqlClientMvc client = new GraphqlClientMvc(
                new RestTemplateBuilder()
                        .rootUri(url)
                        .build(),
                new ObjectMapper());

        Optional<FindAllPricesQuery.Data> rsp = client.exchange(new FindAllPricesQuery());
        Assert.assertTrue(rsp.isPresent());
        Assert.assertNotNull(rsp.get().getFindAllPrices());
        assertEquals(rsp.get().getFindAllPrices().get(0).getPrice(), 1500.0);
        assertEquals(rsp.get().getFindAllPrices().get(1).getPrice(), 2000.0);
        assertEquals(rsp.get().getFindAllPrices().get(2).getPrice(), 1000.0);
        assertEquals(rsp.get().getFindAllPrices().get(3).getPrice(), 700.0);
    }

    @Test
    public void FindPricesByVehicleId() throws IOException {
        GraphqlClientMvc client = new GraphqlClientMvc(
                new RestTemplateBuilder()
                        .rootUri(url)
                        .build(),
                new ObjectMapper());

        Optional<FindPricesByVehicleIdQuery.Data> rsp = client.exchange(new FindPricesByVehicleIdQuery(3));
        Assert.assertTrue(rsp.isPresent());
        Assert.assertNotNull(rsp.get().getFindPricesByVehicleId());
        assertEquals(rsp.get().getFindPricesByVehicleId().get(0).getPrice(), 1000.0);
        assertEquals(rsp.get().getFindPricesByVehicleId().get(1).getPrice(), 700.0);
    }

    @Test
    public void GenerateAndAssignPrice() throws IOException {
        GraphqlClientMvc client = new GraphqlClientMvc(
                new RestTemplateBuilder()
                        .rootUri(url)
                        .build(),
                new ObjectMapper());

        Optional<GenerateAndAssignPriceMutation.Data> rsp = client.exchange(new GenerateAndAssignPriceMutation("ABC", 2));
        Assert.assertTrue(rsp.isPresent());
        Assert.assertNotNull(rsp.get().getGenerateAndAssignPrice());
        assertNotNull(rsp.get().getGenerateAndAssignPrice().getPrice());
        assertEquals(rsp.get().getGenerateAndAssignPrice().getCurrency(), "ABC");
        assertEquals(rsp.get().getGenerateAndAssignPrice().getVehicle_id(), 2);
    }


}
