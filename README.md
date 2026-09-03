# Digital Wallet & Banking REST API

A Java Spring Boot REST API for a digital wallet and banking system — customer accounts, wallets, deposits, withdrawals, transfers, and transaction history, built with a layered architecture and Spring Data JPA.

**Status:** In development. This project is being built incrementally, phase by phase, with a focus on transactional integrity and clean backend design rather than surface-level CRUD.

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA / Hibernate
* MySQL
* Maven

(Spring Security with JWT-based authentication and authorization will be added in a later phase.)

## Progress

* [x] Project setup (Spring Boot + MySQL connection)
* [x] Core entities and relationships (Customer, Wallet, Transaction, User)
* [x] Customer management endpoints (create, get by ID, get all, update, delete)
* [x] Basic global exception handling
* [x] Wallet management endpoints (create, get by customer ID, get by wallet ID)
* [x] One wallet per customer rule
* [x] Deposit operation with transaction recording
* [x] Transactional balance updates using `@Transactional`
* [ ] Transaction history with pagination
* [ ] Withdrawal / transfer logic
* [ ] Validation & centralized exception handling improvements
* [ ] Authentication & authorization
* [ ] Automated tests
* [ ] API documentation

## Entity Relationships

* A `Customer` has one `Wallet`, and one linked `User` account for authentication.
* A `Wallet` has many `Transaction` records.
* Transfers are represented as two linked transaction rows (debit + credit), so transaction records are always immutable — only ever inserted, never updated.

## Architecture

The project follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL Database
```

DTOs are used for API requests and responses, while mappers handle conversion between DTOs and entities.

## Current API

### Create Customer

```http
POST /api/customers
```

Example request:

```json
{
  "fullName": "Ahmed Sabra",
  "email": "ahmed@example.com"
}
```

### Get Customer by ID

```http
GET /api/customers/{id}
```

Example:

```http
GET /api/customers/1
```

### Get All Customers

```http
GET /api/customers
```

### Update Customer

```http
PUT /api/customers/{id}
```

Example:

```http
PUT /api/customers/1
```

### Delete Customer

```http
DELETE /api/customers/{id}
```

### Create Wallet

```http
POST /api/customers/{customerId}/wallet
```

### Get Wallet by Customer ID

```http
GET /api/customers/{customerId}/wallet
```

### Get Wallet by Wallet ID

```http
GET /api/wallets/{walletId}
```

### Deposit

```http
POST /api/wallets/{walletId}/deposit
```

Example request:

```json
{
  "amount": 500.00
}
```

The deposit operation updates the wallet balance and records the operation as a `DEPOSIT` transaction within a transactional operation.

If a requested customer or wallet does not exist, the API currently returns a `404 Not Found` response through the global exception handling layer.

## Setup

```bash
git clone https://github.com/ahmedsabra01/java-digital-wallet-api.git
cd java-digital-wallet-api

# create the database
mysql -u root -p -e "CREATE DATABASE wallet_db;"

# set your MySQL password in src/main/resources/application.properties

mvn spring-boot:run
```

## Roadmap

* Add transaction response and transaction history with pagination
* Add withdrawal and transfer operations
* Improve validation and centralized exception handling
* Add authentication and authorization using Spring Security and JWT
* Add automated tests
* Add API documentation with Swagger / OpenAPI
* Add architecture and database diagrams

---

*This README will be expanded as the project progresses. Full API documentation, architecture diagrams, database details, and authentication setup will be added as each phase is completed.*
