package com.udacity.pricingservicegraphql.resolver;

import com.udacity.pricingservicegraphql.entity.Price;
import com.udacity.pricingservicegraphql.exception.PriceNotFoundException;
import com.udacity.pricingservicegraphql.repository.PriceRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Query implements GraphQLQueryResolver {
    private PriceRepository priceRepository;

    public Query(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Iterable<Price> findAllPrices() {
        return priceRepository.findAll();
    }

    public List<Price> findPricesByVehicleId(Long vehicle_id) {
        List<Price> listPrices = priceRepository.findAllByVehicleId(vehicle_id);
        if (listPrices.size() == 0) {
            throw new PriceNotFoundException("Price not found for Vehicle Id", vehicle_id);
        } else {
            return listPrices;
        }
    }


}
