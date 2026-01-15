package com.leojcl.trading.dto.trade;

import com.leojcl.trading.domain.TradeSide;
import com.leojcl.trading.domain.TradingSymbol;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeRequest {

    @NotNull
    private TradingSymbol symbol;

    @NotNull
    private TradeSide side;

    @NotNull
    @DecimalMin("0.0000001")
    private BigDecimal quantity;
}
