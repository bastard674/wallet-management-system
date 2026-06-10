# Wallet Management System - Database Design

## Overview

The Wallet Management System consists of three primary entities:

1. User
2. Wallet
3. Transaction

Each user owns exactly one wallet.

A wallet maintains the current balance of the user.

Every money movement is recorded in the transaction table for auditing and tracking purposes.

---

## Entity Relationship Diagram

```text
User (1) -------- (1) Wallet

Wallet (1) ------ (*) Transaction

Transaction
    |
    |---- sender_wallet_id
    |
    |---- receiver_wallet_id
```

---

## Users Table

Stores user profile and authentication information.

| Column Name  | Data Type    | Constraints      |
| ------------ | ------------ | ---------------- |
| user_id      | BIGINT       | Primary Key      |
| name         | VARCHAR(100) | Not Null         |
| phone_number | VARCHAR(15)  | Unique, Not Null |
| password     | VARCHAR(255) | Not Null         |
| created_at   | TIMESTAMP    | Not Null         |
| updated_at   | TIMESTAMP    | Not Null         |

### Notes

* Phone number must be unique.
* Password will be stored in encrypted format using BCrypt.
* One user can have only one wallet.

---

## Wallets Table

Stores wallet information and current balance.

| Column Name | Data Type     | Constraints                 |
| ----------- | ------------- | --------------------------- |
| wallet_id   | BIGINT        | Primary Key                 |
| user_id     | BIGINT        | Foreign Key (users.user_id) |
| balance     | DECIMAL(19,2) | Not Null                    |
| created_at  | TIMESTAMP     | Not Null                    |
| updated_at  | TIMESTAMP     | Not Null                    |

### Notes

* Wallet is automatically created during user registration.
* Initial balance is 0.00.
* One wallet belongs to exactly one user.

---

## Transactions Table

Stores all wallet transactions.

| Column Name        | Data Type     | Constraints                     |
| ------------------ | ------------- | ------------------------------- |
| transaction_id     | BIGINT        | Primary Key                     |
| sender_wallet_id   | BIGINT        | Foreign Key (wallets.wallet_id) |
| receiver_wallet_id | BIGINT        | Foreign Key (wallets.wallet_id) |
| amount             | DECIMAL(19,2) | Not Null                        |
| transaction_type   | VARCHAR(20)   | Not Null                        |
| status             | VARCHAR(20)   | Not Null                        |
| created_at         | TIMESTAMP     | Not Null                        |

### Transaction Types

* DEPOSIT
* WITHDRAW
* TRANSFER

### Transaction Status

* SUCCESS
* FAILED
* PENDING

---

## Registration Flow

1. User submits registration request.
2. User record is created.
3. Wallet record is automatically created.
4. Wallet balance is initialized to 0.00.

---

## Login Flow

1. User logs in using phone number and password.
2. Credentials are validated.
3. JWT token is generated.
4. Token is used to access secured APIs.

---

## Dashboard Flow

1. User sends JWT token.
2. System identifies logged-in user.
3. Wallet details are fetched.
4. Recent transactions are fetched.
5. Dashboard response is returned.

---

## Phase 1 APIs

### Register User

```http
POST /api/users/register
```

### Login

```http
POST /api/auth/login
```

### Dashboard

```http
GET /api/dashboard
```

### Deposit Money

```http
POST /api/wallet/deposit
```

---

## Future Enhancements

* Money Transfer API
* Transaction History API
* JWT Authentication and Authorization
* Optimistic Locking
* Pessimistic Locking
* Docker Support
* Cloud Deployment
* Admin Dashboard
* Notification Service
