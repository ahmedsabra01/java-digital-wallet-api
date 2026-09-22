# Digital Wallet & Banking REST API

A Java Spring Boot REST API for a digital wallet and banking system — customer accounts, wallets, deposits, withdrawals, transfers, authentication, and transaction history, built with a layered architecture and Spring Data JPA.

**Status:** In development. This project is being built incrementally, phase by phase, with a focus on transactional integrity, clean backend design, secure authentication, and real-world banking workflows rather than surface-level CRUD.

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA / Hibernate
* MySQL
* Maven
* Spring Security
* BCrypt password hashing

JWT-based authentication and authorization are currently being implemented.

## Progress

* [x] Project setup (Spring Boot + MySQL connection)
* [x] Core entities and relationships (Customer, Wallet, Transaction, User)
* [x] Customer management endpoints (create, get by ID, get all, update, delete)
* [x] Global exception handling
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
* [x] Request validation using Jakarta Bean Validation
* [x] Centralized validation error handling
* [x] Insufficient balance handling
* [x] Same-wallet transfer validation
* [x] Generic exception fallback handling
* [x] Spring Security configuration
* [x] BCrypt password hashing
* [x] User registration
* [x] Username and email uniqueness validation
* [x] Registration endpoint
* [ ] Login authentication
* [ ] JWT generation and validation
* [ ] Endpoint authorization
* [ ] Resource ownership validation
* [ ] Automated tests
* [ ] API documentation

## Entity Relationships

* A `Customer` has one `Wallet`, and one linked `User` account for authentication.
* A `Wallet` has many `Transaction` records.
* Each transaction belongs to a single wallet.
* A transfer is represented by two transaction records: a `TRANSFER_OUT` transaction for the sender and a `TRANSFER_IN` transaction for the receiver.
* Transfer transactions are linked using `relatedTransactionId` to connect the sender and receiver sides of the same transfer.
* A `User` represents the authentication account and is linked to one `Customer`.

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

The service layer contains the main business logic, including wallet balance updates, transaction recording, validation, transfer processing, and user registration.

Spring Security is responsible for the security layer before requests reach the application controllers.

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

### Authentication

#### Register User

```http
POST /api/auth/register
```

Example request:

```json
{
  "fullName": "Ahmed Sabra",
  "email": "ahmed@example.com",
  "username": "ahmed",
  "password": "Ahmed123"
}
```

During registration:

1. The request is validated using Jakarta Bean Validation.
2. The system checks whether the username already exists.
3. The system checks whether the email already exists.
4. A new `Customer` is created.
5. The password is hashed using BCrypt.
6. A new `User` authentication account is created.
7. The `User` is linked to the `Customer`.
8. The operation is executed inside a `@Transactional` boundary.

Passwords are never stored as plain text.

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
2. Prevents transfers to the same w
