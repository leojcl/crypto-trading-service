package com.leojcl.trading.repository;

import com.leojcl.trading.domain.MarketPrice;
import com.leojcl.trading.domain.TradingSymbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {

    Optional<MarketPrice> findTopBySymbolOrderByTimestampDesc(TradingSymbol symbol);
}
