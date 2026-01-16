package com.leojcl.trading.controller;


import com.leojcl.trading.dto.trade.TradeRequest;
import com.leojcl.trading.dto.trade.TradeResponse;
import com.leojcl.trading.service.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<TradeResponse> executeTrade(@RequestHeader("X-USER-ID") Long userId, @Valid @RequestBody TradeRequest request){

        TradeResponse response = tradeService.executeTrade(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TradeResponse>> getTradingHistory(@RequestHeader("X-USER-ID") Long userId){

        List<TradeResponse> history = tradeService.getUserTradingHistory(userId);
        return ResponseEntity.ok(history);

    }
}
