package com.playtomic.tests.wallet;

import com.playtomic.tests.wallet.domain.Balance;

import java.math.BigDecimal;
import java.util.Random;

public class BalanceExamples {
    public static Balance random() {
        Random random = new Random();
        int randomValue = random.nextInt(10000);
        return new Balance(BigDecimal.valueOf(randomValue));
    }
}
