package com.leojcl.trading.service;

import com.leojcl.trading.domain.TradingSymbol;
import com.leojcl.trading.domain.price.AggregatedPrice;
import com.leojcl.trading.domain.price.ExchangePrice;
import com.leojcl.trading.integration.price.ExchangePriceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceAggregationService {

    // Inject all beans implements ExchangePriceClient
    private final List<ExchangePriceClient> exchangePriceClients;

    public AggregatedPrice aggregatedBestPrice(TradingSymbol symbol){

        List<ExchangePrice> prices = exchangePriceClients.stream()
                .map(client -> safeGetPrice(client, symbol))
                .filter(Objects::nonNull)
                .toList();

        if(prices.isEmpty()){
            throw new IllegalStateException("No price available for symbol " + symbol);
        }

        // Best bid = max of bid price
        var bestBid = prices.stream()
                .map(ExchangePrice::getBidPrice)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalStateException("No valid bid price found"));

        // Best ask = min of ask price
        var bestAsk = prices.stream()
                .map(ExchangePrice::getAskPrice)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalStateException("No valid ask price found"));

        Instant lastedTimestamp = prices.stream()
                .map(ExchangePrice::getTimestamp)
                .max(Comparator.naturalOrder())
                .orElse(Instant.now());

        return AggregatedPrice.builder()
                .symbol(symbol)
                .bestBid(bestBid)
                .bestAsk(bestAsk)
                .timestamp(lastedTimestamp)
                .build();
    }

    private ExchangePrice safeGetPrice(ExchangePriceClient client, TradingSymbol symbol){
        try{
            return client.getPrice(symbol);
        }catch (Exception ex){
            log.warn("Failed to get price from source {} for symbol {}: {}",
                    client.getSourceName(), symbol, ex.getMessage());
            return  null;
        }
    }
}
