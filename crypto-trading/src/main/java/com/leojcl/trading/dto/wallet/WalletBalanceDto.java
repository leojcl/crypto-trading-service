package com.leojcl.trading.dto.wallet;

import com.leojcl.trading.domain.Asset;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletBalanceDto {

    private Asset asset;
    private BigDecimal balance;
}
