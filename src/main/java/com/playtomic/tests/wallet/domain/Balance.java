package com.playtomic.tests.wallet.domain;

import java.math.BigDecimal;

public class Balance {
    private BigDecimal amount;

    public Balance(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal amount() {
        return amount;
    }

    public void increase(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }
}
