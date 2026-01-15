package com.leojcl.trading.controller;

import com.leojcl.trading.domain.MarketPrice;
import com.leojcl.trading.domain.TradingSymbol;
import com.leojcl.trading.dto.price.LastestPriceResponse;
import com.leojcl.trading.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping("/{symbol}")
    public ResponseEntity<LastestPriceResponse> getLastestPrice(@PathVariable TradingSymbol symbol){

        MarketPrice marketPrice = priceService.getLastedPrice(symbol);

        LastestPriceResponse response = LastestPriceResponse.builder()
                .symbol(marketPrice.getSymbol())
                .bidPrice(marketPrice.getBidPrice())
                .askPrice(marketPrice.getAskPrice())
                .timestamp(marketPrice.getTimestamp())
                .build();

        return  ResponseEntity.ok(response);
    }

}
