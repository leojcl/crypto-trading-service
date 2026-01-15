package com.leojcl.trading.service;

import com.leojcl.trading.domain.MarketPrice;
import com.leojcl.trading.domain.TradingSymbol;
import com.leojcl.trading.domain.price.AggregatedPrice;
import com.leojcl.trading.repository.MarketPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceAggregationService priceAggregationService;
    private final MarketPriceRepository marketPriceRepository;

    @Transactional
    public MarketPrice refreshAndSaveAggregatedPrice(TradingSymbol symbol){

        AggregatedPrice aggregatedPrice = priceAggregationService.aggregatedBestPrice(symbol);

        MarketPrice entity = MarketPrice.builder()
                .symbol(symbol)
                .bidPrice(aggregatedPrice.getBestBid())
                .askPrice(aggregatedPrice.getBestAsk())
                .timestamp(aggregatedPrice.getTimestamp())
                .build();

        return marketPriceRepository.save(entity);
    }

    public MarketPrice getLastedPrice(TradingSymbol symbol){

        return marketPriceRepository.findTopBySymbolOrderByTimestampDesc(symbol)
                .orElseThrow(() -> new IllegalStateException(
                        "No market price available for symbol "+ symbol
                ));
    }
}
