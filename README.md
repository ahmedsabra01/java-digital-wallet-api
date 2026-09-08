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
* [x] Wallet-to-wallet transfer operation
* [x] Transactional balance updates using `@Transactional`
* [x] Deposit response with transaction details
* [x] Withdrawal response with transaction details
* [x] Transfer response with sender and receiver details
* [x] Transaction response mapping
* [x] Transaction history with pagination
* [x] Basic request validation using Jakarta Bean Validation
* [x] Insufficient balance handling
* [x] Same-wallet transfer validation
* [ ] Validation & centralized exception handling improvements
* [ ] Authentication & authorization
* [ ] Automated tests
* [ ] API documentation

## Entity Relationships

* A `Customer` has one `Wallet`, and one linked `User` account for authentication.
* A `Wallet` has many `Transaction` records.
* Each transaction belongs to a single wallet.
* A transfer is represented by two transaction records: a `TRANSFER_OUT` transaction for the sender and a `TRANSFER_IN` transaction for the receiver.
* Transfer transactions are linked using `relatedTransactionId` to connect the sender and receiver sides of the same transfer.

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

The service layer contains the main business logic, including wallet balance updates, transaction recording, validation, and transfer processing.

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

If the requested amount exceeds the current wallet balance, the API returns an insufficient balance error and the transaction is not completed.

---

### Wallet-to-Wallet Transfer

```http
POST /api/wallets/{walletId}/transfer
```

The `{walletId}` represents the sender wallet.

Example request:

```json
{
  "receiverWalletId": 2,
  "amount": 1200.00
}
```

Example response:

```json
{
  "receiverWalletId": 2,
  "senderWalletId": 1,
  "amount": 1200,
  "senderBalanceAfter": 100.00,
  "receiverBalanceAfter": 1200.00,
  "receiverTransactionId": 7,
  "senderTransactionId": 6,
  "createdAt": "2026-09-08T08:18:35"
}
```

The transfer operation:

1. Validates the sender and receiver wallets.
2. Prevents transfers to the same wallet.
3. Checks that the sender has sufficient balance.
4. Deducts the amount from the sender wallet.
5. Adds the amount to the receiver wallet.
6. Creates a `TRANSFER_OUT` transaction for the sender.
7. Creates a `TRANSFER_IN` transaction for the receiver.
8. Links the two transaction records using `relatedTransactionId`.
9. Executes the entire operation inside a single `@Transactional` boundary.

This ensures that the balance changes and transaction records are committed as one atomic operation.

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
* Transfer to the same wallet

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

Financial operations such as deposits, withdrawals, and transfers use Spring's `@Transactional` to ensure that wallet balance updates and transaction records are handled as a single atomic operation.

For example, during a transfer:

```text
Validate request
      ↓
Find sender and receiver wallets
      ↓
Validate balance and business rules
      ↓
Update sender balance
      ↓
Update receiver balance
      ↓
Create TRANSFER_OUT transaction
      ↓
Create TRANSFER_IN transaction
      ↓
Link related transactions
      ↓
Commit transaction
```

If an error occurs during the operation, the transaction can be rolled back to prevent wallet balances and transaction history from becoming inconsistent.

---

## Transaction Design

Transactions represent financial events associated with a wallet.

For deposits and withdrawals, a single transaction record is created.

For wallet-to-wallet transfers, the system creates two transaction records:

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

The two records represent the two sides of the same transfer and are linked using `relatedTransactionId`.

This design makes the transfer traceable from either wallet's transaction history and provides an auditable record of the movement of funds.

---

## Validation

Request DTOs use Jakarta Bean Validation for basic input validation.

Examples include:

* Required fields using `@NotNull`
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
* [x] Wallet-to-wallet transfer
* [x] Transaction history

### Backend Improvements

* [ ] Improve request validation
* [ ] Expand centralized exception handling
* [ ] Add authentication and authorization
* [ ] Implement Spring Security with JWT
* [ ] Add automated unit and integration tests
* [ ] Add API documentation with Swagger / OpenAPI
* [ ] Improve concurrency handling for financial operations

### Project Documentation

* [ ] Add architecture diagram
* [ ] Add database ER diagram
* [ ] Add API documentation
* [ ] Add authentication flow documentation

---

*This README will be expanded as the project progresses. The project is intentionally being developed incrementally, with each phase focusing on real backend concepts, transactional integrity, and business rules rather than simply implementing CRUD operations.*
