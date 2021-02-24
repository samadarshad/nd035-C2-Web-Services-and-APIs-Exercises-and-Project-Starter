package com.udacity.pricingservicegraphql.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal price;
    private String currency;
    private Long vehicle_id;

    public Price(Long id, BigDecimal price, String currency, Long vehicle_id) {
        this.id = id;
        this.price = price;
        this.currency = currency;
        this.vehicle_id = vehicle_id;
    }

    public Price() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
}
