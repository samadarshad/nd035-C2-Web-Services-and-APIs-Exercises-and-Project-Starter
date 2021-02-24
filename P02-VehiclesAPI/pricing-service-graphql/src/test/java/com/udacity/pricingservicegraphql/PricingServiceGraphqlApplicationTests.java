package com.udacity.pricingservicegraphql;


import com.jayway.jsonpath.JsonPath;
import com.udacity.pricingservicegraphql.entity.Price;
import com.udacity.pricingservicegraphql.repository.PriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:PricingServiceGraphqlApplicationTests"})
class PricingServiceGraphqlApplicationTests {
    @LocalServerPort
    private int port;

    private static final RestTemplate restTemplate = new RestTemplate();
    private String url;

    @BeforeAll
    static void beforeAll(@Autowired PriceRepository priceRepository) {
        priceRepository.save(new Price(null, new BigDecimal(1500d), "USD", 1L));
        priceRepository.save(new Price(null, new BigDecimal(2000d), "USD", 2L));
        priceRepository.save(new Price(null, new BigDecimal(1000d), "USD", 3L));
        priceRepository.save(new Price(null, new BigDecimal(700d), "GBP", 3L));
    }

    @BeforeEach
    void beforeEach() {
        url = "http://localhost:" + port + "/graphql";
    }

    @Test
    void listPrices() {
        String expectedResponse = "{\"data\":{\"findAllPrices\":[{\"id\":\"1\",\"price\":1500.0,\"currency\":\"USD\",\"vehicle_id\":1},{\"id\":\"2\",\"price\":2000.0,\"currency\":\"USD\",\"vehicle_id\":2},{\"id\":\"3\",\"price\":1000.0,\"currency\":\"USD\",\"vehicle_id\":3},{\"id\":\"4\",\"price\":700.0,\"currency\":\"GBP\",\"vehicle_id\":3}]}}";
        String request = "{\n" +
                "    \"query\":\"{findAllPrices { id price currency vehicle_id} }\"\n" +
                "}";

        String result = restTemplate.postForObject(url, request, String.class);
        assertEquals(result, expectedResponse);
    }

    @Test
    void listPricesForVehicle() {
        String request = "{\n" +
                "    \"query\":\"{findPricesByVehicleId(vehicle_id: 3) { id price currency vehicle_id} }\"\n" +
                "}";

        String result = restTemplate.postForObject(url, request, String.class);
        assertEquals((double) JsonPath.read(result, "$.data.findPricesByVehicleId[0].price"), 1000.0);
        assertEquals((double) JsonPath.read(result, "$.data.findPricesByVehicleId[1].price"), 700.0);
    }

    @Test
    void generateAndAssignPrice() {
        String request = "{\n" +
                "    \"query\":\"mutation{generateAndAssignPrice(currency: \\\"GBP\\\", vehicle_id: 3) { id price currency vehicle_id} }\"\n" +
                "}";

        String result = restTemplate.postForObject(url, request, String.class);
        assertNotNull(JsonPath.read(result, "$.data.generateAndAssignPrice.price"));
        assertEquals(JsonPath.read(result, "$.data.generateAndAssignPrice.vehicle_id"), (Integer) 3);
        assertEquals(JsonPath.read(result, "$.data.generateAndAssignPrice.currency"), "GBP");
    }

}



//    generateAndAssignPrice(currency: String!, vehicle_id: Int!): Price!
//        newPrice(price: Float!, currency: String!, vehicle_id: Int!) : Price!
//        updatePrice(id: Int!, price: Float, currency: Int, vehicle_id: Int): Price!
//        deletePrice(id: Int!): Boolean!
//        deleteAllByVehicleId(vehicle_id: Int!): Boolean!