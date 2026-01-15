# crypto-trading-service
Crypto trading service assignment with price aggregation and trading APIs

## Functional Scope
- Aggregate crypto prices from Binance, Houbi
- Retrieve the lastest best aggregated price
- Execute buy and sell orders based on best aggregate price
- Retrieve user wallet balance
- Retrieve user trading history

## Tech stack
- Java 21
- Spring boot
- H2 Database
- Maven


Execute query for trade: 

select * from wallet_balance;

select * from market_price;

select * from users;

INSERT INTO users (id, username, password, status, email, create_at, updated_at)
VALUES (1, 'test_user', '123', 'ACTIVE', 'luom.ha.step24@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO wallet_balance (user_id, asset, balance)
VALUES (1, 'USDT', 50000.00000000);

SELECT * FROM wallet_balance WHERE user_id = 1;