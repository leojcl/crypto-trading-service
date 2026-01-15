package com.leojcl.trading.integration.price;

import com.leojcl.trading.domain.TradingSymbol;
import com.leojcl.trading.domain.price.ExchangePrice;
import com.leojcl.trading.dto.intergration.ApiBinancePriceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class BinanceExchangePriceClient implements ExchangePriceClient {

    private final RestTemplateBuilder restTemplateBuilder;
    private final SymbolMapper symbolMapper;

    @Value("${price.api.binance.url}")
    private String binanceUrl;

    private RestTemplate restTemplate(){
        return restTemplateBuilder.build();
    }


    @Override
    public String getSourceName() {
        return "BINANCE";
    }

    @Override
    public ExchangePrice getPrice(TradingSymbol symbol) {

        // call api to return list of bookTicker
        ApiBinancePriceResponse[] tickers = restTemplate().getForObject(binanceUrl, ApiBinancePriceResponse[].class);

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
                .bidPrice(matched.getBidPrice())
                .askPrice(matched.getAskPrice())
                .source(getSourceName())
                .timestamp(Instant.now())
                .build();
    }
}
