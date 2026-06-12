# Retailer Rewards API

## Overview

This project implements a Retailer Rewards Program using Spring Boot.

A retailer offers reward points to customers based on each recorded purchase transaction.

### Reward Rules

- 2 points for every dollar spent above $100
- 1 point for every dollar spent between $50 and $100
- No points for amounts below $50

### Example

Transaction Amount = $120

Reward Points:

- $50 to $100 = 50 points
- $100 to $120 = 20 × 2 = 40 points

Total Reward Points = 90

---

## Features

- Customer Management APIs
- Reward Calculation APIs
- Dynamic Monthly Reward Calculation
- Total Reward Calculation
- Global Exception Handling
- H2 In-Memory Database
- Unit and Integration Tests
- DAO Pattern Implementation
- DTO and Mapper Pattern

---

## Technology Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5
- Mockito

---

## Project Structure

```text
src/main/java/com/retailer/rewards

├── config
├── constants
├── controller
├── dao
├── dto
├── entity
├── enums
├── exception
├── mapper
├── repository
├── service
└── RewardsApplication.java

---

## Database

The application uses an H2 in-memory database.

### H2 Console

URL:

```text
http://localhost:8080/h2-console
```

Connection Details:

```text
JDBC URL : jdbc:h2:mem:rewardsdb

Username : sa

Password :
```

---

## API Endpoints

### Get All Customers

Returns all customers available in the system.

**Endpoint**

```http
GET /api/v1/allCustomers
```

### Sample Response

```json
"customers": [
        {
            "customerId": 1,
            "customername": "John Smith",
            "monthlyRewards": [
                {
                    "month": "May",
                    "points": 230
                },
                {
                    "month": "June",
                    "points": 0
                },
                {
                    "month": "May",
                    "points": 22
                },
                {
                    "month": "June",
                    "points": 320
                }
            ],
            "totalPoints": 572
        },{
            "customerId": 20,
            "customername": "Evelyn Lewis",
            "monthlyRewards": [
                {
                    "month": "May",
                    "points": 76
                },
                {
                    "month": "May",
                    "points": 199
                }
            ],
            "totalPoints": 275
        }
    ]
```

---

### Get Customer By Id

Returns customer details for a given customer id.

**Endpoint**

```http
GET /api/v1/customers/{customerId}
```

### Example

```http
GET /api/v1/customers/4
```

### Sample Response

```json
"customer": {
        "customerId": 4,
        "customername": "Emma Wilson",
        "monthlyRewards": [
            {
                "month": "June",
                "points": 39
            },
            {
                "month": "May",
                "points": 133
            },
            {
                "month": "May",
                "points": 265
            },
            {
                "month": "May",
                "points": 305
            },
            {
                "month": "May",
                "points": 0
            },
            {
                "month": "June",
                "points": 0
            },
            {
                "month": "May",
                "points": 14
            }
        ],
        "totalPoints": 756
    }

---

## Reward Calculation Logic

### Amount Less Than $50

```text
0 Points
```

### Amount Between $50 And $100

```text
Amount - 50
```

Example:

```text
$80 = 30 Points
```

### Amount Greater Than $100

```text
50 + ((Amount - 100) × 2)
```

Example:

```text
$120

50 + (20 × 2)

= 90 Points
```

---

## Exception Handling

Global exception handling is implemented using:

```java
@RestControllerAdvice
```

### Handled Exceptions

- CustomerNotFoundException
- IllegalArgumentException
- Generic Exception

### Sample Error Response

```json
{
  "message": "Customer not found with id: 100"
}
```

---

## Test Coverage

The project contains:

- Service Layer Unit Tests
- Controller Layer Tests
- Positive Scenarios
- Negative Scenarios
- Exception Scenarios

---

## Running The Application

Clone repository:

```bash
git clone <repository-url>
```

Navigate to project:

```bash
cd rewards
```

Build project:

```bash
mvn clean install
```

Run application:

```bash
mvn spring-boot:run
```

Application starts on:

```text
http://localhost:8080
```

---

## Assignment Requirements Covered

✔ Spring Boot REST API

✔ Dynamic Month Calculation (No Hardcoded Months)

✔ Customer Reward Calculation

✔ Monthly Reward Aggregation

✔ Total Reward Aggregation

✔ Customer CRUD APIs

✔ DAO Layer Implementation

✔ DTO and Mapper Pattern

✔ Global Exception Handling

✔ Unit Tests

✔ H2 Database

✔ Java Coding Standards

✔ JavaDoc Documentation
