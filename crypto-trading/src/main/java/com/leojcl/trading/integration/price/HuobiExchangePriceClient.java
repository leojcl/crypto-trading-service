package com.leojcl.trading.integration.price;

import com.leojcl.trading.domain.TradingSymbol;
import com.leojcl.trading.domain.price.ExchangePrice;
import com.leojcl.trading.dto.intergration.ApiHuobiPriceResponse;
import com.leojcl.trading.dto.intergration.ApiHuobiTickerItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class HuobiExchangePriceClient implements ExchangePriceClient {

    private final RestClient  restClient;
    private final SymbolMapper symbolMapper;

    @Value("${price.api.huobi.url}")
    private String huobiUrl;

    @Override
    public String getSourceName() {
        return "HUOBI";
    }

    @Override
    public ExchangePrice getPrice(TradingSymbol symbol) {

        try{

            ApiHuobiPriceResponse response = restClient.get()
                    .uri(huobiUrl)
                    .retrieve()
                    .body(ApiHuobiPriceResponse.class);

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
                    .bidPrice(Objects.requireNonNull(matched.getBid(), "Bid price cannot be null"))
                    .askPrice(Objects.requireNonNull(matched.getAsk(), "Ask price cannot be null"))
                    .source(getSourceName())
                    .timestamp(Instant.now())
                    .build();
        }catch (Exception ex){
            log.error("Error fetching price from Huobi: {}", ex.getMessage());
            throw new IllegalStateException("Failed to fetch price from Huobi: " + ex.getMessage(), ex);
        }
    }
}
