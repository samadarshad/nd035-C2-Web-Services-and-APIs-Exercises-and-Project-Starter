package com.udacity.pricingservicegraphql;


import com.udacity.pricingservicegraphql.entity.Price;
import com.udacity.pricingservicegraphql.repository.PriceRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.chrono.JapaneseEra;

import static graphql.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PricingServiceGraphqlApplicationTests {
//    @LocalServerPort
//    private int randomServerPort;

    @LocalServerPort
    private int port;

//    @Autowired
//    MockMvc mockMvc;

//    @Autowired
//    private GraphQLTestTemplate graphQLTestTemplate;
//    @Autowired
//    private MockMvc mvc;

//    @Autowired
//    private TestRestTemplate restTemplate;

    @Autowired
    private PriceRepository priceRepository;

//    @Before
//    public void setUp() {
//        priceRepository.save(new Price(null, new BigDecimal(1500d), "USD", 1L));
//        priceRepository.save(new Price(null, new BigDecimal(2000d), "USD", 2L));
//        priceRepository.save(new Price(null, new BigDecimal(1000d), "USD", 3L));
//        priceRepository.save(new Price(null, new BigDecimal(700d), "GBP", 3L));
//        priceRepository.save(new Price(null, new BigDecimal(700d), "GBP", 3L));
//        priceRepository.save(new Price(null, new BigDecimal(700d), "GBP", 3L));
//    }

    @Test
    void listPrices() throws Exception {
        String expectedResponse = "{\n" +
                "    \"data\": {\n" +
                "        \"findAllPrices\": [\n" +
                "            {\n" +
                "                \"id\": \"1\",\n" +
                "                \"price\": 1500.0,\n" +
                "                \"currency\": \"USD\",\n" +
                "                \"vehicle_id\": 1\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"2\",\n" +
                "                \"price\": 2000.0,\n" +
                "                \"currency\": \"USD\",\n" +
                "                \"vehicle_id\": 2\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"3\",\n" +
                "                \"price\": 1000.0,\n" +
                "                \"currency\": \"USD\",\n" +
                "                \"vehicle_id\": 3\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"4\",\n" +
                "                \"price\": 700.0,\n" +
                "                \"currency\": \"GBP\",\n" +
                "                \"vehicle_id\": 3\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";


//        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/find-all-prices.graphql");

//        assertNotNull(response);
        String request = "{\n" +
                "    \"query\":\"{findAllPrices { id price currency vehicle_id} }\"\n" +
                "}";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpRequest = new HttpEntity<String>(request, headers);
        String url = "http://localhost:" + port + "/graphql";
        String result = restTemplate.postForObject(url, request, String.class);

        assert(jsonPath(result, "$.data.findAllPrices[0].price", is(1500.0)));




    }

}
