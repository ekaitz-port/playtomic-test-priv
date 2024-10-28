package com.playtomic.tests.wallet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class AmountExamples {
    public static BigDecimal random() {
        Random random = new Random();
        double randomValue = random.nextDouble(1000);
        return BigDecimal.valueOf(randomValue).setScale(2, RoundingMode.HALF_UP);
    }
}
