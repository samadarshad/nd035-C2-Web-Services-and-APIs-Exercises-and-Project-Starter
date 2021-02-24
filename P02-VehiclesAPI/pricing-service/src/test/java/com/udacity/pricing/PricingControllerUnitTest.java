package com.udacity.pricing;

import com.udacity.pricing.api.PricingController;
import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(PricingController.class)
public class PricingControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PricingService pricingService;

    @Before
    public void setup() throws PriceException {
        Price price = new Price("USD", new BigDecimal(12345), 1L);
        when(pricingService.getPrice(1L)).thenReturn(price);
        when(pricingService.getPrice(21L)).thenThrow(PriceException.class);
    }

    @Test
    public void getPrice() throws Exception {
        mockMvc.perform(get("/services/price").param("vehicleId", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.price", is(12345)))
                .andExpect(jsonPath("$.vehicleId", is(1)))
                .andExpect(jsonPath("$.currency", is("USD")));

        verify(pricingService, times(1)).getPrice(1L);
    }

}
