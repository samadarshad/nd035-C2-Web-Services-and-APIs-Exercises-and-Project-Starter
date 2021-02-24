package com.udacity.pricingservicegraphql.service;

import com.udacity.pricingservicegraphql.entity.Price;
import com.udacity.pricingservicegraphql.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PriceService {
    private final PriceRepository priceRepository;
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price generatePrice(String currency, Long vehicle_id) {
        return new Price(null, randomPrice(), currency, vehicle_id);
    }

    private static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }
}
