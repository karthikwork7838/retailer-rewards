# Retailer Rewards Program - Spring Boot REST API

## Overview

This project implements a **Retailer Rewards Program** using **Spring Boot**.

A retailer offers reward points to customers based on purchase transactions.

### Reward Calculation Rules

- **2 points** for every dollar spent **above $100**
- **1 point** for every dollar spent **between $50 and $100**
- **0 points** for amounts **below $50**

### Example

For a purchase of **$120**:

Reward points:

(120 - 100) × 2 = 40 points

(100 - 50) × 1 = 50 points

**Total = 90 reward points**

---

## Assignment Requirements Covered

### Reward Calculation

- Reward points calculated based on transaction amount
- Monthly rewards calculated dynamically
- Total rewards calculated for each customer

### Dynamic Month Handling

Months are **not hardcoded**.

Reward calculation derives months dynamically from transaction dates using Java Date APIs.

Example:

```json
{
  "January": 90,
  "February": 30,
  "March": 130
}
```

This works for any month range without code changes.

### RESTful APIs

Implemented APIs for:

- Create Customer
- Update Customer
- Delete Customer
- Get Customer By ID
- Get All Customers
- Get Rewards By Customer
- Get Rewards For All Customers

### Unit and Integration Testing

The project includes:

- Unit tests
- Integration tests
- Negative test scenarios
- Exception test coverage

### Exception Handling

Global exception handling is implemented using:

```txt
@RestControllerAdvice
```

Custom exception:

```txt
CustomerNotFoundException
```

### Coding Standards

The project follows Java coding standards:

- Proper package structure
- Meaningful class names
- Standard naming conventions
- Service layer separation
- DAO pattern implementation
- DTO pattern usage
- Mapper pattern usage
- JavaDoc documentation

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5
- Mockito

---

## Project Structure

```txt
src
├── main
│   ├── java
│   │   └── com.retailer.rewards
│   │       ├── controller
│   │       ├── dao
│   │       │   └── impl
│   │       ├── dto
│   │       ├── entity
│   │       ├── exception
│   │       ├── mapper
│   │       ├── repository
│   │       ├── service
│   │       │   └── impl
│   │       ├── constants
│   │       └── RewardsApplication
│   │
│   └── resources
│       ├── application.properties
│       ├── schema.sql
│       └── data.sql
│
└── test
    └── java
```

---

## Design Pattern Used

### DAO Pattern

Traditional DAO pattern used to separate persistence logic from service layer.

Example:

```txt
CustomerDao
CustomerDaoImpl
TransactionDao
TransactionDaoImpl
```

### DTO Pattern

DTOs are used to separate request/response objects from entities.

Example:

```txt
CustomerRequestDto
CustomerResponseDto
RewardResponseDto
TransactionResponseDto
```

### Mapper Pattern

Mapper classes are used to convert between DTOs and entities.

Example:

```txt
CustomerEntityMapper
TransactionEntityMapper
```

---

## Database

### H2 In-Memory Database

This project uses **H2 Database** for simplicity and easy local execution.

Database is initialized automatically using:

```txt
schema.sql
data.sql
```

### H2 Console

Access:

```txt
http://localhost:8080/h2-console
```

Configuration:

```txt
JDBC URL:
jdbc:h2:mem:rewardsdb

Username:
sa

Password:
(password empty)
```

---

## Sample Dataset

The application contains:

- **20 customers**
- Multiple transactions
- Three-month transaction history
- Different purchase amounts for testing reward logic

Example transaction:

```txt
Customer ID: 1
Amount: $120
Transaction Date: 2026-01-10
```

---

## API Endpoints

### Customer APIs

### Create Customer

**POST**

```http
/api/customers
```

Request:

```json
{
  "name": "Karthik"
}
```

Response:

```json
{
  "id": 21,
  "name": "Karthik"
}
```

---

### Get All Customers

**GET**

```http
/api/customers
```

---

### Get Customer By ID

**GET**

```http
/api/customers/{customerId}
```

Example:

```http
/api/customers/1
```

---

### Update Customer

**PUT**

```http
/api/customers/{customerId}
```

Request:

```json
{
  "name": "Updated Customer"
}
```

---

### Delete Customer

**DELETE**

```http
/api/customers/{customerId}
```

Example:

```http
/api/customers/1
```

---

## Reward APIs

### Get Rewards For All Customers

**GET**

```http
/api/rewards
```

Response:

```json
[
  {
    "customerId": 1,
    "customerName": "John Doe",
    "monthlyRewards": {
      "January": 90,
      "February": 30,
      "March": 130
    },
    "totalRewards": 250
  }
]
```

---

### Get Rewards By Customer ID

**GET**

```http
/api/rewards/customer/{customerId}
```

Example:

```http
/api/rewards/customer/1
```

---

## Exception Handling

### Customer Not Found

Response:

```json
{
  "message": "Customer not found with id: 999"
}
```

HTTP Status:

```txt
404 NOT FOUND
```

### Internal Server Error

Response:

```json
{
  "message": "An unexpected error occurred"
}
```

HTTP Status:

```txt
500 INTERNAL SERVER ERROR
```

---

## Reward Calculation Logic

### Amount less than $50

```txt
0 points
```

### Amount between $50 and $100

```txt
(amount - 50)
```

Example:

```txt
$80 → 30 points
```

### Amount greater than $100

```txt
(amount - 100) × 2
+
50
```

Example:

```txt
$120

20 × 2 = 40
+
50

Total = 90 points
```

---

## How to Run the Project

### Clone Repository

```bash
git clone <repository-url>
```

### Navigate to project

```bash
cd retailer-rewards
```

### Run application

Using Maven:

```bash
mvn spring-boot:run
```

Or run:

```txt
RewardsApplication.java
```

from IntelliJ.

---

## Testing

Run tests using:

```bash
mvn test
```

Or in IntelliJ:

```txt
Right Click → Run Tests
```

---

## Future Improvements

Potential enhancements:

- Pagination support
- Swagger/OpenAPI documentation
- Authentication and authorization
- Docker support
- MySQL/PostgreSQL integration
- Transaction management
- API versioning

---

## Author

Karthik