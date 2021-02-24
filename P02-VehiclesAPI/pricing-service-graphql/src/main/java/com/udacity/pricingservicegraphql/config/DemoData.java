package com.udacity.pricingservicegraphql.config;

import com.udacity.pricingservicegraphql.entity.Price;
import com.udacity.pricingservicegraphql.repository.PriceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoData {
    @Bean
    CommandLineRunner initPriceDatabase(PriceRepository priceRepository) {
        return args -> {
            priceRepository.save(new Price(1L, 1500L, "USD", 1L));
            priceRepository.save(new Price(2L, 2000L, "USD", 2L));
            priceRepository.save(new Price(3L, 1000L, "USD", 3L));
            priceRepository.save(new Price(4L, 700L, "GBP", 3L));
        };
    }
}
