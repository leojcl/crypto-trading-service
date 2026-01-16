package com.leojcl.trading.service;

import com.leojcl.trading.domain.*;
import com.leojcl.trading.dto.trade.TradeRequest;
import com.leojcl.trading.dto.trade.TradeResponse;
import com.leojcl.trading.repository.TradeRepository;
import com.leojcl.trading.repository.WalletBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final PriceService priceService;
    private final WalletBalanceRepository walletBalanceRepository;
    private final TradeRepository tradeRepository;

    @Transactional
    public TradeResponse executeTrade(Long userId, TradeRequest request){

        // get lasted aggregate price
        MarketPrice lastestPrice = priceService.getLastedPrice(request.getSymbol());
        if(lastestPrice == null){
            throw  new IllegalStateException("No price available for symbol " + request.getSymbol());
        }

        // determine asset
        Asset baseAsset = getBaseAsset(request.getSymbol());
        Asset quoteAsset = Asset.USDT;

        BigDecimal quantity = request.getQuantity();
        BigDecimal price = resolvePrice(lastestPrice, request.getSide());
        BigDecimal quoteAmount = price.multiply(quantity);

        // load wallet balance
        WalletBalance baseBalance = walletBalanceRepository.findByUserIDAndAsset(userId, baseAsset)
                .orElse(new WalletBalance(null, userId, baseAsset, BigDecimal.ZERO));

        WalletBalance quoteBalance = walletBalanceRepository
                .findByUserIDAndAsset(userId, quoteAsset)
                .orElse(new WalletBalance(null, userId, quoteAsset, BigDecimal.ZERO));

        // logic for BUY
        if (request.getSide() == TradeSide.BUY) {
            if (quoteBalance.getBalance().compareTo(quoteAmount) < 0) {
                throw new IllegalStateException("Insufficient balance: USDT required " + quoteAmount
                        + ", available: " + quoteBalance.getBalance()
                );
            }
            quoteBalance.setBalance(quoteBalance.getBalance().subtract(quoteAmount));
            baseBalance.setBalance(baseBalance.getBalance().add(quantity));
        }

        // logic for SELL
        else if (request.getSide() == TradeSide.SELL) {

            if (baseBalance.getBalance().compareTo(quantity) < 0) {
                throw new IllegalStateException("Insufficient"  + baseAsset + "balance. required " + quantity
                       + ", available: " + baseBalance.getBalance()
                );
            }
            baseBalance.setBalance(baseBalance.getBalance().subtract(quantity));
            quoteBalance.setBalance(quoteBalance.getBalance().add(quoteAmount));
        }

        // save balance
        walletBalanceRepository.save(quoteBalance);
        walletBalanceRepository.save(baseBalance);

        // save transaction history trade
        Trade trade = Trade.builder()
                .userId(userId)
                .symbol(request.getSymbol())
                .side(request.getSide())
                .price(price)
                .quantity(quantity)
                .quoteAmount(quoteAmount)
                .createdAt(Instant.now())
                .build();

        trade = tradeRepository.save(trade);

        // return response DTO
        return TradeResponse.builder()
                .tradeId(trade.getId())
                .symbol(trade.getSymbol())
                .side(trade.getSide())
                .price(trade.getPrice())
                .quantity(trade.getQuantity())
                .quoteAmount(trade.getQuoteAmount())
                .createdAt(trade.getCreatedAt())
                .build();
    }

    private Asset getBaseAsset(TradingSymbol symbol){
        return switch (symbol){
            case BTCUSDT -> Asset.BTC;
            case ETHUSDT -> Asset.ETH;
        };
    }

    private BigDecimal resolvePrice(MarketPrice marketPrice, TradeSide side){
        // buy use ask price, sell use bid price
        return  side == TradeSide.BUY
                ? marketPrice.getAskPrice() : marketPrice.getBidPrice();
    }
}
