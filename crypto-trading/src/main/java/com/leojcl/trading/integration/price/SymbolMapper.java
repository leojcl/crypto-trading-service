package com.leojcl.trading.integration.price;

import com.leojcl.trading.domain.TradingSymbol;
import org.springframework.stereotype.Component;

@Component
public class SymbolMapper {

    public String toBinanceSymbol(TradingSymbol symbol){
        return symbol.name();
    }

    public String toHuobiSymbol(TradingSymbol symbol){
        return symbol.name().toUpperCase();
    }

}
