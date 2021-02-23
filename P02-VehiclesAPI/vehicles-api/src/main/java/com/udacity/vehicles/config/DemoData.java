package com.udacity.vehicles.config;

import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoData {

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
    @Bean
    CommandLineRunner initCarDatabase(CarRepository carRepository, ManufacturerRepository manufacturerRepository) {
        return args -> {
            for (int i = 0; i < 5; i++) {
                manufacturerRepository.save(new Manufacturer(100 + i, "Manufac" + String.valueOf(i)));
            }
            for (int i = 0; i < 10; i++) {
                carRepository.save(getCar(i, manufacturerRepository));
            }
        };
    }
}
