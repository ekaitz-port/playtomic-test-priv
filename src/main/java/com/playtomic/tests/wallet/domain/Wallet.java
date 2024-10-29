package com.playtomic.tests.wallet.domain;

import java.math.BigDecimal;

public class Wallet {
    public static final double MINIMUM_AMOUNT_TO_TOPUP = 5.0;
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

    public void topUp(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(MINIMUM_AMOUNT_TO_TOPUP)) < 0) {
            throw new TopUpAmountLessThanMinimum(amount);
        }

        balance.increase(amount);
    }
}
