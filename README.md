# BackendDevTest - Backend Technical Test

Spring Boot application that exposes an API to retrieve similar products for a given product.

## Requirements

- Java 17
- Maven 3.8+

## Installation

```bash
mvn install
```

## Scripts

```bash
mvn spring-boot:run    # Development mode
mvn package            # Build production jar
mvn test               # Run tests
```

## Tech stack

- Java 17
- Spring Boot 3
- Maven

## API

Exposes the following endpoint:

`GET /product/{productId}/similar`

Returns a list of similar products with their full details.

## Author

Patricia Mato Miragaya - patriciamato10@gmail.com

