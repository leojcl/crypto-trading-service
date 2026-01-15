package com.leojcl.trading.integration.price;

import com.leojcl.trading.domain.TradingSymbol;
import com.leojcl.trading.domain.price.ExchangePrice;
import com.leojcl.trading.dto.intergration.ApiBinancePriceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class BinanceExchangePriceClient implements ExchangePriceClient {

    private final SymbolMapper symbolMapper;
    private final RestClient restClient;

    @Value("${price.api.binance.url}")
    private String binanceUrl;


    @Override
    public String getSourceName() {
        return "BINANCE";
    }

    @Override
    public ExchangePrice getPrice(TradingSymbol symbol) {

        try{
            // call api to return list of bookTicker
            ApiBinancePriceResponse[] tickers = restClient.get()
                    .uri(binanceUrl)
                    .retrieve()
                    .body(ApiBinancePriceResponse[].class);

            if(tickers == null || tickers.length == 0){
                throw new IllegalStateException("BINANCE: empty response from bookTicker API");
            }

            String targetSymbol = symbolMapper.toBinanceSymbol(symbol);

            ApiBinancePriceResponse matched = Arrays.stream(tickers)
                    .filter(t -> targetSymbol.equalsIgnoreCase(t.getSymbol()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "BINANCE: sysmbol " + targetSymbol + " not found in Book Ticker response "
                    ));

            return ExchangePrice.builder()
                    .symbol(symbol)
                    .bidPrice(Objects.requireNonNull(matched.getBidPrice(), "Bid price cannot be null"))
                    .askPrice(Objects.requireNonNull(matched.getAskPrice(), "Ask price cannot be null"))
                    .source(getSourceName())
                    .timestamp(Instant.now())
                    .build();
        } catch (Exception ex){
            log.error("Error fetching price from Binance: {}", ex.getMessage());
            throw new IllegalStateException("Failed to fetch price from Binance: " + ex.getMessage(), ex);
        }
    }
}
