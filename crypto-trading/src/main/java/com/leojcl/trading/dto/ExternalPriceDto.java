package com.leojcl.trading.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalPriceDto {

    private String symbol;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
}
