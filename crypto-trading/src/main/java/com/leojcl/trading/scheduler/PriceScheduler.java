package com.leojcl.trading.scheduler;

import com.leojcl.trading.domain.TradingSymbol;
import com.leojcl.trading.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PriceScheduler {

    private final PriceService priceService;

    @Scheduled(fixedDelay = 10_000)
    public void updatePrice(){
        try{
            priceService.refreshAndSaveAggregatedPrice(TradingSymbol.BTCUSDT);
            priceService.refreshAndSaveAggregatedPrice(TradingSymbol.ETHUSDT);

            log.debug("Refreshed aggregated prices for BTCUSDT and ETHUSDT");
        }catch (Exception ex){
            log.error("Error while refreshing aggregated prices", ex);
        }
    }
}
