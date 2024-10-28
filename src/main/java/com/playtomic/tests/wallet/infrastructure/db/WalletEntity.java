package com.playtomic.tests.wallet.infrastructure.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
@Getter
@AllArgsConstructor
public class WalletEntity {
    @Id
    private String id;
    private BigDecimal balance;

    public WalletEntity() {

    }
}
