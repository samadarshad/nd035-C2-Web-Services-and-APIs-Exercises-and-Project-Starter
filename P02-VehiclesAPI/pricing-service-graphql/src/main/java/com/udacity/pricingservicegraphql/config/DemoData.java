package com.udacity.pricingservicegraphql.config;

import com.udacity.pricingservicegraphql.entity.Price;
import com.udacity.pricingservicegraphql.repository.PriceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
@Configuration
public class DemoData {
    @Bean()
    CommandLineRunner initPriceDatabase(PriceRepository priceRepository) {
        return args -> {
            priceRepository.save(new Price(1L, new BigDecimal(1500d), "USD", 1L));
            priceRepository.save(new Price(2L, new BigDecimal(2000d), "USD", 2L));
            priceRepository.save(new Price(3L, new BigDecimal(1000d), "USD", 3L));
            priceRepository.save(new Price(4L, new BigDecimal(700d), "GBP", 3L));
        };
    }
}
