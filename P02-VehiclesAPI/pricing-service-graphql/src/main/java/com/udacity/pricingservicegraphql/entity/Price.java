package com.udacity.pricingservicegraphql.entity;

import javax.persistence.*;

@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long price;
    private String currency;
    private Long vehicle_id;

    public Price(Long id, Long price, String currency, Long vehicle_id) {
        this.id = id;
        this.price = price;
        this.currency = currency;
        this.vehicle_id = vehicle_id;
    }

    public Price() {

    }
}
