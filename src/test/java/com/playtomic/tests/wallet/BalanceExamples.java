package com.playtomic.tests.wallet;

import com.playtomic.tests.wallet.domain.Balance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class BalanceExamples {
    public static Balance random() {
        Random random = new Random();
        double randomValue = random.nextDouble(10000);
        return new Balance(BigDecimal.valueOf(randomValue).setScale(2, RoundingMode.HALF_UP));
    }
}
