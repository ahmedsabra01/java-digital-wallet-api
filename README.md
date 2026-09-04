# Digital Wallet & Banking REST API

A Java Spring Boot REST API for a digital wallet and banking system — customer accounts, wallets, deposits, withdrawals, transfers, and transaction history, built with a layered architecture and Spring Data JPA.

**Status:** In development. This project is being built incrementally, phase by phase, with a focus on transactional integrity, clean backend design, and real-world banking workflows rather than surface-level CRUD.

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA / Hibernate
* MySQL
* Maven

Spring Security with JWT-based authentication and authorization will be added in a later phase.

## Progress

* [x] Project setup (Spring Boot + MySQL connection)
* [x] Core entities and relationships (Customer, Wallet, Transaction, User)
* [x] Customer management endpoints (create, get by ID, get all, update, delete)
* [x] Basic global exception handling
* [x] Wallet management endpoints (create, get by customer ID, get by wallet ID)
* [x] One wallet per customer rule
* [x] Deposit operation with transaction recording
* [x] Withdrawal operation with transaction recording
* [x] Transactional balance updates using `@Transactional`
* [x] Deposit response with transaction details
* [x] Withdrawal response with transaction details
* [x] Transaction response mapping
* [x] Transaction history with pagination
* [x] Insufficient balance handling
* [ ] Transfer logic
* [ ] Validation & centralized exception handling improvements
* [ ] Authentication & authorization
* [ ] Automated tests
* [ ] API documentation

## Entity Relationships

* A `Customer` has one `Wallet`, and one linked `User` account for authentication.
* A `Wallet` has many `Transaction` records.
* Each transaction belongs to a single wallet.
* Transfers will be represented as two linked transaction rows (debit + credit), allowing transaction records to remain immutable — inserted once and never updated.

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

The service layer contains the main business logic, including wallet balance updates and transaction management.

## Current API

### Customer Management

#### Create Customer

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

#### Get Customer by ID

```http
GET /api/customers/{id}
```

Example:

```http
GET /api/customers/1
```

#### Get All Customers

```http
GET /api/customers
```

#### Update Customer

```http
PUT /api/customers/{id}
```

Example:

```http
PUT /api/customers/1
```

#### Delete Customer

```http
DELETE /api/customers/{id}
```

---

### Wallet Management

#### Create Wallet

```http
POST /api/customers/{customerId}/wallet
```

Each customer can have only one wallet.

#### Get Wallet by Customer ID

```http
GET /api/customers/{customerId}/wallet
```

#### Get Wallet by Wallet ID

```http
GET /api/wallets/{walletId}
```

---

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

Example response:

```json
{
  "transactionId": 1,
  "walletId": 1,
  "transactionType": "DEPOSIT",
  "amount": 500.00,
  "balanceAfter": 500.00,
  "createdAt": "2026-09-03T10:30:00"
}
```

The deposit operation updates the wallet balance and records the operation as a `DEPOSIT` transaction within the same transactional operation.

---

### Withdrawal

```http
POST /api/wallets/{walletId}/withdraw
```

Example request:

```json
{
  "amount": 200.00
}
```

Example response:

```json
{
  "transactionId": 2,
  "walletId": 1,
  "transactionType": "WITHDRAWAL",
  "amount": 200.00,
  "balanceAfter": 300.00,
  "createdAt": "2026-09-03T10:35:00"
}
```

The withdrawal operation validates the requested amount, checks the wallet balance, updates the balance, and records the withdrawal as a `WITHDRAWAL` transaction.

If the requested amount exceeds the current wallet balance, the API returns an `Insufficient Balance` error and the transaction is not completed.

---

### Get Wallet Transaction History

```http
GET /api/wallets/{walletId}/transactions
```

The transaction history endpoint supports pagination.

Example:

```http
GET /api/wallets/1/transactions?page=0&size=10
```

Transactions are returned as `TransactionResponse` objects and include details such as:

* Transaction ID
* Wallet ID
* Transaction type
* Amount
* Balance after the transaction
* Related transaction ID
* Creation timestamp

---

## Error Handling

The API uses a centralized global exception handling layer through `@RestControllerAdvice`.

Currently handled business exceptions include:

* Customer not found
* Wallet not found
* Wallet already exists for customer
* Insufficient wallet balance

Example error response:

```json
{
  "timestamp": "2026-09-03T10:40:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Insufficient Balance",
  "path": "/api/wallets/1/withdraw"
}
```

---

## Transaction Management

Financial operations such as deposits and withdrawals use Spring's `@Transactional` to ensure that wallet balance updates and transaction records are handled as a single atomic operation.

For example, during a withdrawal:

```text
Validate request
      ↓
Check wallet balance
      ↓
Update wallet balance
      ↓
Create transaction record
      ↓
Commit transaction
```

If an error occurs during the operation, the transaction can be rolled back to prevent the wallet balance and transaction history from becoming inconsistent.

---

## Transaction Design

Transactions are treated as immutable records.

A transaction is created when a financial operation occurs and is not modified afterward.

For future transfers, the system will create two related transaction records:

```text
Sender Wallet
     │
     └── TRANSFER_OUT
              │
              └── relatedTransactionId
                       │
                       └── TRANSFER_IN
                              │
                              └── Receiver Wallet
```

This design keeps the transaction history auditable and avoids modifying historical transaction records.

---

## Validation

Request DTOs use Jakarta Bean Validation for basic input validation.

Examples include:

* Required fields using `@NotBlank` and `@NotNull`
* Email format validation using `@Email`
* Positive monetary amounts using `@DecimalMin`

Financial amounts are represented using Java `BigDecimal` to provide precise decimal arithmetic suitable for monetary calculations.

---

## Setup

Clone the repository:

```bash
git clone https://github.com/ahmedsabra01/java-digital-wallet-api.git
cd java-digital-wallet-api
```

Create the MySQL database:

```bash
mysql -u root -p -e "CREATE DATABASE wallet_db;"
```

Configure your MySQL credentials in:

```text
src/main/resources/application.properties
```

Then run the application:

```bash
mvn spring-boot:run
```

The API will start using the configured Spring Boot server port.

---

## Roadmap

### Core Banking Operations

* [x] Customer management
* [x] Wallet creation and retrieval
* [x] Deposit
* [x] Withdrawal
* [x] Transaction history
* [ ] Transfer between wallets

### Backend Improvements

* [ ] Improve validation
* [ ] Expand centralized exception handling
* [ ] Add authentication and authorization
* [ ] Implement Spring Security with JWT
* [ ] Add automated tests
* [ ] Add API documentation with Swagger / OpenAPI

### Project Documentation

* [ ] Add architecture diagram
* [ ] Add database ER diagram
* [ ] Add API documentation
* [ ] Add authentication flow documentation

---

## Future Enhancements

The project may be extended with additional banking features such as:

* Wallet-to-wallet transfers
* Transfer transaction linking
* Authentication and authorization
* Role-based access control
* Transaction filtering and sorting
* Improved concurrency handling
* Automated integration and unit testing
* Swagger / OpenAPI documentation

---

*This README will be expanded as the project progresses. The project is intentionally being developed incrementally, with each phase focusing on real backend concepts and business rules rather than simply implementing CRUD operations.*
