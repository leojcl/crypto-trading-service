package com.leojcl.trading.controller;

import com.leojcl.trading.dto.wallet.WalletBalanceDto;
import com.leojcl.trading.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/leoJclWallet")
    public ResponseEntity<List<WalletBalanceDto>> getMyWalletBalances(
            @RequestHeader("X-USER-ID") Long userId) {

        List<WalletBalanceDto> balances = walletService.getUserWalletBalance(userId);
        return ResponseEntity.ok(balances);
    }
}
