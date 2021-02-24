package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getPrice() {
        ResponseEntity<Price> response = restTemplate.getForEntity("http://localhost:" + port + "/services/price?vehicleId=1", Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        Price price = response.getBody();
        assertNotNull(price.getPrice());
    }

    @Test
    public void getPriceNotFound() {
        ResponseEntity<Price> response = restTemplate.getForEntity("http://localhost:" + port + "/services/price?vehicleId=21", Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

        Price price = response.getBody();
        assertNull(price.getPrice());
    }



}
