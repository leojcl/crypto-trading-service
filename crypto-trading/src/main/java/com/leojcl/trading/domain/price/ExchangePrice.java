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
public class ExchangePrice {

    private TradingSymbol symbol;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
    private String source;
    private Instant timestamp;
}
