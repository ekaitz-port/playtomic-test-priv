package com.playtomic.tests.wallet.domain.examples;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class AmountExamples {
    public static BigDecimal random() {
        Random random = new Random();
        double randomValue = random.nextDouble(1000);
        return BigDecimal.valueOf(randomValue).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal randomGreaterThan5() {
        return random().add(BigDecimal.valueOf(6.0));
    }

    public static BigDecimal randomLessThan5() {
        Random random = new Random();
        double randomValue = random.nextDouble(5);
        return BigDecimal.valueOf(randomValue).setScale(2, RoundingMode.HALF_UP);
    }
}
