package com.playtomic.tests.wallet.domain;

import java.math.BigDecimal;

public class Wallet {
    private final WalletId id;
    private final Balance balance;

    public Wallet(WalletId id, Balance balance) {
        this.id = id;
        this.balance = balance;
    }

    public String idAsString() {
        return id.asString();
    }

    public BigDecimal balanceAmount() {
        return balance.amount();
    }
}
