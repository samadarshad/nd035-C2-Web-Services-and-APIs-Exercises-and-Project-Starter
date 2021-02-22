package com.udacity.pricingmicroservice.repository;

import com.udacity.pricingmicroservice.entity.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Long> {
}