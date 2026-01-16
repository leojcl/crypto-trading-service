package com.leojcl.trading.service;

import com.leojcl.trading.domain.WalletBalance;
import com.leojcl.trading.dto.wallet.WalletBalanceDto;
import com.leojcl.trading.repository.WalletBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletBalanceRepository walletBalanceRepository;

    public List<WalletBalanceDto> getUserWalletBalance(Long userId) {

        List<WalletBalance> balances = walletBalanceRepository.findByUserID(userId);

        return balances.stream()
                .map(balance -> WalletBalanceDto.builder()
                        .asset(balance.getAsset())
                        .balance(balance.getBalance())
                        .build())
                .toList();
    }

}
