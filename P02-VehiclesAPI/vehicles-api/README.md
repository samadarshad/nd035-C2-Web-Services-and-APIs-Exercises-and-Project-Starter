# Vehicles API

A REST API to maintain vehicle data and to provide a complete
view of vehicle details including price and address.

## Features

- REST API exploring the main HTTP verbs and features
- Hateoas
- Custom API Error handling using `ControllerAdvice`
- Swagger API docs
- HTTP WebClient
- MVC Test
- Automatic model mapping

## Instructions

#### Getting Started

To properly run this application you need to start 
1) eureka-server 
2) boogle-maps 
3) pricing-service-graphql (you must first do `mvn compile` to generate code)
4) vehicles-api (you must first do `mvn compile` to generate code)

in order.
Then visit http://localhost:8080/swagger-ui/index.html and do GET for vehicle id = 1. Notice the price and location data are populated.

#### Running tests
You can run tests that don't require all 4 services to be up. There are tests in the pricing-service-graphql (you need to generate code using `mvn compile`) and vehicle-api project.
You can also visit http://localhost:8762/graphiql for querying the pricing-service-graphql service.

## Operations

Swagger UI: http://localhost:8080/swagger-ui/index.html

### Create a Vehicle

`POST` `/cars`
```json
{
   "condition":"USED",
   "details":{
      "body":"sedan",
      "model":"Impala",
      "manufacturer":{
         "code":101,
         "name":"Chevrolet"
      },
      "numberOfDoors":4,
      "fuelType":"Gasoline",
      "engine":"3.6L V6",
      "mileage":32280,
      "modelYear":2018,
      "productionYear":2018,
      "externalColor":"white"
   },
   "location":{
      "lat":40.73061,
      "lon":-73.935242
   }
}
```

### Retrieve a Vehicle

`GET` `/cars/{id}`

This feature retrieves the Vehicle data from the database
and access the Pricing Service and Boogle Maps to enrich 
the Vehicle information to be presented

### Update a Vehicle

`PUT` `/cars/{id}`

```json
{
   "condition":"USED",
   "details":{
      "body":"sedan",
      "model":"Impala",
      "manufacturer":{
         "code":101,
         "name":"Chevrolet"
      },
      "numberOfDoors":4,
      "fuelType":"Gasoline",
      "engine":"3.6L V6",
      "mileage":32280,
      "modelYear":2018,
      "productionYear":2018,
      "externalColor":"white"
   },
   "location":{
      "lat":40.73061,
      "lon":-73.935242
   }
}
```

### Delete a Vehicle

`DELETE` `/cars/{id}`
