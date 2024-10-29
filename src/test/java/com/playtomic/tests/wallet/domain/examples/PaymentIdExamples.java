package com.playtomic.tests.wallet.domain.examples;

import com.playtomic.tests.wallet.domain.PaymentId;

import java.util.UUID;

public class PaymentIdExamples {
    public static PaymentId random() {
        return new PaymentId(UUID.randomUUID().toString());
    }
}
