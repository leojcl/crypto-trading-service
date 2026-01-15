package com.leojcl.trading.dto.intergration;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiBinancePriceResponse {

    private String symbol;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
}
