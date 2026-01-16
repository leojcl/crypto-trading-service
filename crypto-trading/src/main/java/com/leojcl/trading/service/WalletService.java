package com.leojcl.trading.service;

import com.leojcl.trading.domain.Asset;
import com.leojcl.trading.domain.WalletBalance;
import com.leojcl.trading.dto.wallet.WalletBalanceDto;
import com.leojcl.trading.repository.WalletBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletBalanceRepository walletBalanceRepository;

    public List<WalletBalanceDto> getUserWalletBalance(Long userId) {

        List<WalletBalance> balances = walletBalanceRepository.findByUserID(userId);

        // convert to map
        Map<Asset, BigDecimal> balanceMap = balances.stream()
                .collect(Collectors.toMap(
                        WalletBalance::getAsset,
                        WalletBalance::getBalance
                ));

        // add for missing asset = 0 balance
        EnumSet.allOf(Asset.class).forEach(asset ->
                balanceMap.computeIfAbsent(asset, a -> BigDecimal.ZERO)
        );

        // convert back to dto list
        return balanceMap.entrySet().stream()
                .map(entry -> WalletBalanceDto.builder()
                        .asset(entry.getKey())
                        .balance(entry.getValue())
                        .build())
                .toList();
    }
}
