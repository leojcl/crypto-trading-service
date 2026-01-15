package com.leojcl.trading.integration.price;

import com.leojcl.trading.domain.TradingSymbol;
import com.leojcl.trading.domain.price.ExchangePrice;

public interface ExchangePriceClient {

    String getSourceName();
    ExchangePrice getPrice(TradingSymbol symbol);

}
