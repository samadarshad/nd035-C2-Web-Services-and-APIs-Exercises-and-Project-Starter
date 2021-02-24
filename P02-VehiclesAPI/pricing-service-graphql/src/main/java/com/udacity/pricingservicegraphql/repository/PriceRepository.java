package com.udacity.pricingservicegraphql.repository;

import com.udacity.pricingservicegraphql.entity.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface PriceRepository extends CrudRepository<Price, Long> {
    @Query("select price from Price price where price.vehicle_id=:vehicle_id")
    List<Price> findAllByVehicleId(Long vehicle_id);
}
