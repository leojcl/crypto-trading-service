package com.leojcl.trading.dto.intergration;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiHuobiTickerItem {

    private String symbol;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
}
