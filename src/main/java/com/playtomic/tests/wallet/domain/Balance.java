package com.playtomic.tests.wallet.domain;

import java.math.BigDecimal;

public class Balance {
    private final BigDecimal amount;

    public Balance(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal amount() {
        return amount;
    }
}
