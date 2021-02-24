package com.udacity.pricingservicegraphql;


import com.jayway.jsonpath.JsonPath;
import com.udacity.pricingservicegraphql.entity.Price;
import com.udacity.pricingservicegraphql.repository.PriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:PricingServiceGraphqlApplicationTests"})
@AutoConfigureMockMvc
class PricingServiceGraphqlApplicationTests {

    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll(@Autowired PriceRepository priceRepository) {
        priceRepository.save(new Price(null, new BigDecimal(1500d), "USD", 1L));
        priceRepository.save(new Price(null, new BigDecimal(2000d), "USD", 2L));
        priceRepository.save(new Price(null, new BigDecimal(1000d), "USD", 3L));
        priceRepository.save(new Price(null, new BigDecimal(700d), "GBP", 3L));
    }
    @Test
    void listPrices() {
        String expectedResponse = "{\"data\":{\"findAllPrices\":[{\"id\":\"1\",\"price\":1500.0,\"currency\":\"USD\",\"vehicle_id\":1},{\"id\":\"2\",\"price\":2000.0,\"currency\":\"USD\",\"vehicle_id\":2},{\"id\":\"3\",\"price\":1000.0,\"currency\":\"USD\",\"vehicle_id\":3},{\"id\":\"4\",\"price\":700.0,\"currency\":\"GBP\",\"vehicle_id\":3}]}}";
        String request = "{\n" +
                "    \"query\":\"{findAllPrices { id price currency vehicle_id} }\"\n" +
                "}";

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/graphql";
        String result = restTemplate.postForObject(url, request, String.class);
        assertEquals(result, expectedResponse);
    }

    @Test
    void listPricesForVehicle() {
        String request = "{\n" +
                "    \"query\":\"{findPricesByVehicleId(vehicle_id: 3) { id price currency vehicle_id} }\"\n" +
                "}";

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/graphql";
        String result = restTemplate.postForObject(url, request, String.class);
        Assertions.assertEquals((double) JsonPath.read(result, "$.data.findPricesByVehicleId[0].price"), 1000.0);
        Assertions.assertEquals((double) JsonPath.read(result, "$.data.findPricesByVehicleId[1].price"), 700.0);
    }

}
