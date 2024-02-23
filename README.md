---

# Weather Sensor REST API

This REST API is designed for the registration of weather sensors and the collection of atmospheric measurements. It facilitates the addition of temperature and rain data, recorded by the sensors, into the system.

## Getting Started

Clone this repository to set up the local development environment. Ensure that Java and Maven are installed on your system. The application is built with Spring Boot and uses a relational database to store the sensor and measurement data.

### Prerequisites

- Java JDK 8 or higher
- Maven
- Spring Boot
- A relational database (e.g., MySQL, PostgreSQL)

## Endpoints

The API defines the following endpoints:

### Sensor Registration

`POST /sensors/registration`

Registers a new sensor entity within the system.

**Request Body Example:**
```json
{
  "name": "Sensor 1"
}
```
**Validations:**
- The name must be unique within the database.
- The name must not be empty and should have a length of 3 to 30 characters.

Appropriate error responses are returned if the sensor does not meet the required criteria.

### Add Measurement

`POST /measurements/add`

Adds a new measurement datapoint to the database, provided by a registered sensor.

**Request Body Example:**
```json
{
  "value": 22.5,
  "raining": true,
  "sensor": {
    "name": "Sensor 1"
  }
}
```
**Validations:**
- Value is not nullable and should range between -100 and 100.
- Raining is a required boolean field.
- Sensor data must correspond to a registered and validated sensor.

Measurements receive a timestamp upon insertion into the database.

### Get Measurements

`GET /measurements`

This endpoint retrieves all recorded measurements from the system.

### Rainy Days Count

`GET /measurements/rainyDaysCount`

Returns the total count of days with registered rainfall based on the historical data.

## Client Integration

A client can be developed to utilize this API, enabling real-world sensors to send data points and stakeholders to query atmospheric conditions.

---
