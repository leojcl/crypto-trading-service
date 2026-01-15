package com.leojcl.trading.repository;

import com.leojcl.trading.domain.Asset;
import com.leojcl.trading.domain.WalletBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletBalanceRepository extends JpaRepository<WalletBalance, Long> {

    List<WalletBalance> findByUserID(Long userId);

    Optional<WalletBalance> findByUserIDAndAsset(Long userId, Asset asset);
}
