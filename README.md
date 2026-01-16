# crypto-trading-service
Crypto trading service assignment with price aggregation and trading APIs

---

## Functional Scope
- Aggregate crypto prices from Binance, Houbi
- Retrieve the lastest best aggregated price
- Execute buy and sell orders based on best aggregate price
- Retrieve user wallet balance
- Retrieve user trading history

---

## Tech stack
- Java 21
- Spring boot
- H2 Database
- Maven

---

## Task 3: Execute Trade

### Prepare sample data

Create a test user:

```sql
INSERT INTO users (id, username, password, status, email, created_at, updated_at)
VALUES (1, 'test_user', '123', 'ACTIVE', 'luom.ha.step24@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
```
Initialize wallet balance:

```sql
INSERT INTO wallet_balance (user_id, asset, balance)
VALUES (1, 'USDT', 50000.00000000);
```

Execute trade API

Endpoint

POST /api/trades

Request body

```sql
{
  "symbol": "BTCUSDT",
  "side": "BUY",
  "quantity": 0.03
}
```

Verify trade result

```sql
SELECT * FROM users;
SELECT * FROM wallet_balance;
SELECT * FROM wallet_balance WHERE user_id = 1;
SELECT * FROM trade;
```
---

## Task 4: Retrieve User Wallet Balance
Get wallet balances

Endpoint
```sql
GET /api/wallets/{userId}/balances
```

Example
```sql
GET /api/wallets/1/balances
```

---

## Task 5: Retrieve User Trading History
Get trading history

Endpoint
```sql
GET /api/trades/history
```
---

## Final notes 
- This project is designed for assessment purposes.
- No external trading system integration is performed.