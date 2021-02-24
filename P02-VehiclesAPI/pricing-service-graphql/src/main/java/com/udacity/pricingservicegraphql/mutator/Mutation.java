package com.udacity.pricingservicegraphql.mutator;

import com.udacity.pricingservicegraphql.entity.Price;
import com.udacity.pricingservicegraphql.repository.PriceRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {
    private PriceRepository priceRepository;

    public Mutation(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price newPrice(Long price, String currency, Long vehicle_id) {
        Price newPrice = new Price(null, price, currency, vehicle_id);
        priceRepository.save(newPrice);
        return newPrice;
    }
}
