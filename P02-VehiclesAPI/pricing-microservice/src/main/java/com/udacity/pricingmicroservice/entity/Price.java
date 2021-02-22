package com.udacity.pricingmicroservice.entity;

import javax.persistence.*;

@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String price;
    private String currency;
    private Long vehicleid;

    public Price(Long id, String price, String currency, Long vehicleid) {
        this.id = id;
        this.price = price;
        this.currency = currency;
        this.vehicleid = vehicleid;
    }

    public Price() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(Long vehicleId) {
        this.vehicleid = vehicleId;
    }
}
