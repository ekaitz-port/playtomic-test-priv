package com.playtomic.tests.wallet.domain;

import java.math.BigDecimal;

public class TopUpAmountLessThanMinimum extends RuntimeException {
    public TopUpAmountLessThanMinimum(BigDecimal amount) {
        super("Top up amount has to be greater than 5, introduced amount: " + amount);
    }
}
