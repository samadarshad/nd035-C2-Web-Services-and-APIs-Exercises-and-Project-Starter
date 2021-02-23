package com.udacity.vehicles;

import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Launches a Spring Boot application for the Vehicles API,
 * initializes the car manufacturers in the database,
 * and launches web clients to communicate with maps and pricing.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableEurekaClient
public class VehiclesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehiclesApiApplication.class, args);
    }

    /**
     * Initializes the car manufacturers available to the Vehicle API.
     * @param repository where the manufacturer information persists.
     * @return the car manufacturers to add to the related repository
     */
//    @Bean
//    CommandLineRunner initDatabase(ManufacturerRepository repository) {
//        return args -> {
//            repository.save(new Manufacturer(100, "Audi"));
//            repository.save(new Manufacturer(101, "Chevrolet"));
//            repository.save(new Manufacturer(102, "Ford"));
//            repository.save(new Manufacturer(103, "BMW"));
//            repository.save(new Manufacturer(104, "Dodge"));
//        };
//    }

    private Car getCar(ManufacturerRepository manufacturerRepository) {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = manufacturerRepository.getOne(101);
//        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }

    @Bean
    CommandLineRunner initCarDatabase(CarRepository carRepository, ManufacturerRepository manufacturerRepository) {
//        Car car = getCar(manufacturerRepository);
        return args -> {
            manufacturerRepository.save(new Manufacturer(100, "Audi"));
            manufacturerRepository.save(new Manufacturer(101, "Chevrolet"));
            manufacturerRepository.save(new Manufacturer(102, "Ford"));
            manufacturerRepository.save(new Manufacturer(103, "BMW"));
            manufacturerRepository.save(new Manufacturer(104, "Dodge"));
            carRepository.save(getCar(manufacturerRepository));
            carRepository.save(getCar(manufacturerRepository));
            carRepository.save(getCar(manufacturerRepository));
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Web Client for the maps (location) API
     * @param endpoint where to communicate for the maps API
     * @return created maps endpoint
     */
    @Bean(name="maps")
    public WebClient webClientMaps(@Value("${maps.endpoint}") String endpoint) {
        return WebClient.create(endpoint);
    }

    /**
     * Web Client for the pricing API
     * @param endpoint where to communicate for the pricing API
     * @return created pricing endpoint
     */
    @Bean(name="pricing")
    public WebClient webClientPricing(@Value("${pricing.endpoint}") String endpoint, LoadBalancerClient lbClient) {
        return WebClient
                .builder()
                .filter(new LoadBalancerExchangeFilterFunction(lbClient))
                .baseUrl(endpoint)
                .build();
    }
    //(new DefaultWebClientBuilder()).baseUrl(baseUrl).build();

}
