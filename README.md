# Avto Car Rental API

Spring Boot REST API for managing cars, customers, and rentals. It uses JDBC with PostgreSQL and is organized into controller, service, repository, and model layers.

## Requirements

- Java 17+
- Maven
- PostgreSQL

## Configure and run

Create the `avto_car_rental` database. Set the connection values as environment variables:

```sh
export DB_URL=jdbc:postgresql://localhost:5432/avto_car_rental
export DB_USER=postgres
export DB_PASSWORD=your_local_password
mvn spring-boot:run
```

The schema is initialized from `src/main/resources/schema.sql`. The script is idempotent and does not drop tables. Do not commit real credentials.

## Endpoints

| Method | Path | Purpose |
| --- | --- | --- |
| POST | `/api/cars` | Create a car |
| GET | `/api/cars` | List cars |
| GET | `/api/cars/{id}` | Read a car |
| PUT | `/api/cars/{id}` | Update a car |
| DELETE | `/api/cars/{id}` | Delete a car |
| POST | `/api/customers` | Create a customer |
| GET | `/api/customers` | List customers |
| GET | `/api/customers/{id}` | Read a customer |
| POST | `/api/rentals` | Create a rental |
| GET | `/api/rentals` | List rentals |
| GET | `/api/rentals/{id}` | Read a rental |

Example car request:

```json
{
  "type": "ECONOMY",
  "brand": "Toyota",
  "model": "Corolla",
  "pricePerDay": 15000
}
```

Example rental request:

```json
{
  "customerId": 1,
  "carId": 1,
  "days": 3
}
```
