# Digital Wallet & Banking REST API

A Java Spring Boot REST API for a digital wallet and banking system — customer
accounts, wallets, deposits, withdrawals, transfers, and transaction history,
built with a layered architecture and Spring Data JPA.

**Status:** In development. This project is being built incrementally, phase
by phase, with a focus on transactional integrity and clean backend design
rather than surface-level CRUD.

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA / Hibernate
- MySQL
- Maven

(Spring Security with JWT-based authentication and authorization will be
added in a later phase.)

## Progress

- [x] Project setup (Spring Boot + MySQL connection)
- [x] Core entities and relationships (Customer, Wallet, Transaction, User)
- [ ] Customer management endpoints
- [ ] Wallet management endpoints
- [ ] Deposit / withdrawal / transfer logic
- [ ] Transaction history with pagination
- [ ] Validation & centralized exception handling
- [ ] Authentication & authorization
- [ ] Automated tests
- [ ] API documentation

## Entity Relationships

- A `Customer` has one `Wallet`, and one linked `User` account for authentication.
- A `Wallet` has many `Transaction` records.
- Transfers are represented as two linked transaction rows (debit + credit),
  so transaction records are always immutable — only ever inserted, never updated.

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

Full API documentation, request/response examples, and setup details for
authentication will be added as those features are completed.

---

*This README will be expanded as the project progresses — full API docs,
architecture diagram, and setup instructions are coming as each phase is completed.*