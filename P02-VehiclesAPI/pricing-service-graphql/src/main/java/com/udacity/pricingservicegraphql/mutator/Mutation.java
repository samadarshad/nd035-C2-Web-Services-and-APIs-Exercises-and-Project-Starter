package com.udacity.pricingservicegraphql.mutator;

import com.udacity.pricingservicegraphql.entity.Price;
import com.udacity.pricingservicegraphql.exception.PriceNotFoundException;
import com.udacity.pricingservicegraphql.repository.PriceRepository;
import com.udacity.pricingservicegraphql.service.PriceService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {
    private PriceRepository priceRepository;
    private PriceService priceService;

    public Mutation(PriceRepository priceRepository,
                    PriceService priceService) {
        this.priceRepository = priceRepository;
        this.priceService = priceService;
    }

    public Price generateAndAssignPrice(String currency, Long vehicle_id) {
        Price newPrice = priceService.generatePrice(currency, vehicle_id);
        priceRepository.save(newPrice);
        return newPrice;
    }

    public Price newPrice(BigDecimal price, String currency, Long vehicle_id) {
        Price newPrice = new Price(null, price, currency, vehicle_id);
        priceRepository.save(newPrice);
        return newPrice;
    }

    public boolean deleteAllByVehicleId(Long vehicle_id) {
        List<Price> listPrices = priceRepository.findAllByVehicleId(vehicle_id);
        if (listPrices.size() == 0) {
            throw new PriceNotFoundException("Price not found for Vehicle Id", vehicle_id);
        } else {
            for (Price p:listPrices) {
                priceRepository.delete(p);
            }
            return true;
        }
    }

    public boolean deletePrice(Long id) {
        Optional<Price> optionalPrice = priceRepository.findById(id);
        if (optionalPrice.isPresent()) {
            Price deletePrice = optionalPrice.get();
            priceRepository.delete(deletePrice);
            return true;
        } else {
            throw new PriceNotFoundException("Price not found", id);
        }
    }

    public Price updatePrice(Long id, @Nullable BigDecimal price, @Nullable String currency, @Nullable Long vehicle_id) {
        Optional<Price> optionalPrice = priceRepository.findById(id);
        if (optionalPrice.isPresent()) {
            Price newPrice = optionalPrice.get();
            Optional.ofNullable(price).ifPresent(newPrice::setPrice);
            Optional.ofNullable(currency).ifPresent(newPrice::setCurrency);
            Optional.ofNullable(vehicle_id).ifPresent(newPrice::setVehicle_id);
            priceRepository.save(newPrice);
            return newPrice;
        } else {
            throw new PriceNotFoundException("Price not found", id);
        }
    }
}
