package com.leojcl.trading.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "market_price", indexes = {@Index(name = "idx_market_price_symbol_timestamp", columnList = "symbol, timestamp")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TradingSymbol symbol;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal bidPrice;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal askPrice;

    @Column(nullable = false)
    private Instant timestamp;
}
