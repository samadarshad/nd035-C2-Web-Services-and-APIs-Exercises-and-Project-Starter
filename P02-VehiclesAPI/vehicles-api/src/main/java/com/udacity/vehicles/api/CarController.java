package com.udacity.vehicles.api;

import com.udacity.vehicles.config.DemoData;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.service.CarService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping(path = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
class CarController {

    private final CarService carService;
    private final CarResourceAssembler assembler;
    private final DemoData demoData;

    CarController(CarService carService, CarResourceAssembler assembler, DemoData demoData) {
        this.carService = carService;
        this.assembler = assembler;
        this.demoData = demoData;
    }

    /**
     * Creates a list to store any vehicles.
     * @return list of vehicles
     */
    @GetMapping
    CollectionModel<EntityModel<Car>> list() {
        List<EntityModel<Car>> resources = carService.list().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new CollectionModel<>(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }

    @GetMapping("/init")
    ResponseEntity<?> initWithDemoData() {
        demoData.initCarDatabase();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Gets information of a specific car by ID.
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     */
    @GetMapping("/{id}")
    EntityModel<Car> get(@PathVariable Integer id) {
        return assembler.toResource(carService.findById(id));
    }

    /**
     * Posts information to create a new vehicle in the system.
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    ResponseEntity<?> post(@Valid @RequestBody Car car) throws URISyntaxException {
        EntityModel<Car> resource = assembler.toResource(carService.create(car));
        return ResponseEntity.created(new URI(resource.getRequiredLink("self").getHref())).body(resource);
    }

    /**
     * Updates the information of a vehicle in the system.
     * @param id The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */
    @PutMapping("/{id}")
    ResponseEntity<?> put(@PathVariable Integer id, @Valid @RequestBody Car car) throws URISyntaxException {
        car.setId(id);
        EntityModel<Car> resource = assembler.toResource(carService.update(car));
        return ResponseEntity.created(new URI(resource.getRequiredLink("self").getHref())).body(resource);
    }

    /**
     * Removes a vehicle from the system.
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
