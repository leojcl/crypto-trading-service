package com.leojcl.trading.repository;

import com.leojcl.trading.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByUserIdOrderByCreatedAtDesc(Long userId);
}
