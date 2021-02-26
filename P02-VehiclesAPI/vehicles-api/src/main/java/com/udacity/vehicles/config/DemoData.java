package com.udacity.vehicles.config;

import com.netflix.discovery.converters.Auto;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;
import com.udacity.vehicles.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DemoData {

    private final CarService carService;

    private final ManufacturerRepository manufacturerRepository;

    public DemoData(CarService carService, ManufacturerRepository manufacturerRepository) {
        this.carService = carService;
        this.manufacturerRepository = manufacturerRepository;
    }


    private Car getCar(Integer number, ManufacturerRepository manufacturerRepository) {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = manufacturerRepository.getOne(100 + number % 5);
        details.setManufacturer(manufacturer);
        details.setModel("model" + number.toString());
        details.setMileage(32280);
        details.setExternalColor("color" + number.toString());
        details.setBody("body" + number.toString());
        details.setEngine("engine" + number.toString());
        details.setFuelType("fueltype" + number.toString());
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }

    public void initCarDatabase() {
        for (int i = 0; i < 5; i++) {
            manufacturerRepository.save(new Manufacturer(100 + i, "Manufac" + String.valueOf(i)));
        }
        for (int i = 0; i < 10; i++) {
            carService.create(getCar(i, manufacturerRepository));
        }
    }
}
