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
- Spring Boot 3.4.5
- Maven
- WebFlux (WebClient)

## How it works

Given a product ID, the app:
1. Calls the existing API to get the similar product IDs
2. Fetches the details of each similar product in parallel
3. Returns the list of similar products with full details

The app handles errors gracefully. If a product detail is not found, it is excluded from the response instead of failing the whole request.

## API

Exposes the following endpoint:

`GET /product/{productId}/similar`

Returns a list of similar products with their full details.

## Performance

Product details are fetched in parallel using reactive WebClient, which allows handling high concurrency efficiently. All 5 k6 load test scenarios pass successfully (normal, notFound, error, slow, verySlow).

## Testing

The project includes unit and integration tests covering the controller, service and client layers.

```bash
mvn test
```

## Configuration

The mock server URL can be configured in `src/main/resources/application.yaml`:

```yaml
mock:
  base-url: http://localhost:3001
```

## External dependencies

This app requires the mock server running on port 3001 to serve product data.
To start it, clone [backendDevTest](https://github.com/dalogax/backendDevTest) and run:

```bash
docker-compose up -d simulado
```

Once the mock server is running, start the application:

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:5000`.

## Author

Patricia Mato Miragaya