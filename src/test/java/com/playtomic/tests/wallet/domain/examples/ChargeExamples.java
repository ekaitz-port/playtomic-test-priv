package com.playtomic.tests.wallet.domain.examples;

import com.playtomic.tests.wallet.domain.Charge;

public class ChargeExamples {
    public static Charge random() {
        return new Charge(CardExamples.stripeSandbox().number(), AmountExamples.randomGreaterThan5());
    }
}
