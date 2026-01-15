package com.leojcl.trading.dto.trade;

import com.leojcl.trading.domain.TradeSide;
import com.leojcl.trading.domain.TradingSymbol;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeResponse {

    private Long tradeId;
    private TradingSymbol symbol;
    private TradeSide side;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal quoteAmount;
    private Instant createdAt;
}
