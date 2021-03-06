package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final MapsClient mapsClient;
    private final PriceClient priceClient;


    public CarService(CarRepository repository, MapsClient mapsClient, PriceClient priceClient) {
        this.repository = repository;
        this.mapsClient = mapsClient;
        this.priceClient = priceClient;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Integer id) {
        Optional<Car> optionalCar = repository.findById(id);
        Car car;
        if (optionalCar.isPresent()) {
            car = optionalCar.get();
        } else {
            throw new CarNotFoundException();
        }
        car.setPrice(priceClient.getByVehicleId(id));
        car.setLocation(mapsClient.getAddress(car.getLocation()));
        return car;
    }

    public Car create(Car car) {
        car.setId(null);
        repository.save(car);
        priceClient.create("USD", car.getId());
        return repository.getOne(car.getId());
    }

    public Car update(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        Optional.ofNullable(car.getCondition()).ifPresent(carToBeUpdated::setCondition);
                        Optional.ofNullable(car.getDetails()).ifPresent(carToBeUpdated::setDetails);
                        Optional.ofNullable(car.getLocation()).ifPresent(carToBeUpdated::setLocation);
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        } else {
            throw new CarNotFoundException();
        }
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            priceClient.deleteByVehicleId(id);
        } else {
            throw new CarNotFoundException();
        }

    }
}
