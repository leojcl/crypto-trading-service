package com.leojcl.trading.dto.price;

import com.leojcl.trading.domain.TradingSymbol;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LastestPriceResponse {

    private TradingSymbol symbol;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
    private Instant timestamp;
}
