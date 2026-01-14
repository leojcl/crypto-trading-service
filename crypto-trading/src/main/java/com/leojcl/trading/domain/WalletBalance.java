package com.leojcl.trading.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet_balance", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "asset"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userID;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Asset asset;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal balance;


}
