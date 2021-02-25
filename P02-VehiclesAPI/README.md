# ND035-P02-VehiclesAPI-Project

Project repository for JavaND Project 2, where students implement a Vehicles API using Java and Spring Boot that can communicate with separate location and pricing services.

## Instructions

Check each component to see its details and instructions. Note that all three applications
should be running at once for full operation. Further instructions are available in the classroom.

- [Vehicles API](vehicles-api/README.md)
- [Pricing Service](pricing-service/README.md)
- [Boogle Maps](boogle-maps/README.md)

## Dependencies

The project requires the use of Maven and Spring Boot, along with Java v15.

#### Getting Started

To properly run this application you need to start 
1) eureka-server - visit http://localhost:8761 to confirm it is running
2) boogle-maps - http://localhost:9191/maps?lat=20.0&lon=20.0 to confirm running 
3) pricing-service-graphql (you must first do `mvn compile` to generate code) - http://localhost:8762/graphiql and check http://localhost:8761 to confirm it is registered as a eureka client
4) vehicles-api (you must first do `mvn compile` to generate code) - http://localhost:8080/swagger-ui/index.html and check http://localhost:8761 to confirm it is registered as a eureka client

in order.

Then visit the front end of the application: http://localhost:8080/swagger-ui/index.html and do GET for vehicle id = 1. Notice the price and location data are populated. You may also do POST/PUT/DELETE etc, details may be found in [Vehicles API](vehicles-api/README.md) .

#### Running tests
You can run tests that are independant to each service. There are tests in the pricing-service-graphql and vehicle-api project. Note you need to generate code using `mvn compile`.
