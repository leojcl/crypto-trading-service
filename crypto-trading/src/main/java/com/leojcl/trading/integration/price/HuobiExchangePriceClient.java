package com.leojcl.trading.integration.price;

import com.leojcl.trading.domain.TradingSymbol;
import com.leojcl.trading.domain.price.ExchangePrice;
import com.leojcl.trading.dto.intergration.ApiHuobiPriceResponse;
import com.leojcl.trading.dto.intergration.ApiHuobiTickerItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class HuobiExchangePriceClient implements ExchangePriceClient {

    private final RestTemplateBuilder restTemplateBuilder;
    private final SymbolMapper symbolMapper;

    @Value("${price.api.huobi.url}")
    private String huobiUrl;

    private RestTemplate restTemplate(){
        return restTemplateBuilder.build();
    }

    @Override
    public String getSourceName() {
        return "HUOBI";
    }

    @Override
    public ExchangePrice getPrice(TradingSymbol symbol) {
        ApiHuobiPriceResponse response = restTemplate().getForObject(huobiUrl, ApiHuobiPriceResponse.class);

        if(response == null || response.getData() == null || response.getData().isEmpty()){
            throw new IllegalStateException("HUOBI: empty response from market tickers");
        }

        String targgetSymbol = symbolMapper.toHuobiSymbol(symbol);

        ApiHuobiTickerItem matched = response.getData().stream()
                .filter(t -> targgetSymbol.equalsIgnoreCase(t.getSymbol()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "HOUBI: symbol "+ targgetSymbol + " not foud in market tickers"
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
