package com.leojcl.trading.domain.price;

import com.leojcl.trading.domain.TradingSymbol;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AggregatedPrice {

    private TradingSymbol symbol;
    private BigDecimal bestBid;
    private BigDecimal bestAsk;
    private Instant timestamp;
}
