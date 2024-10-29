package com.playtomic.tests.wallet.domain;

public interface PaymentPlatform {
    PaymentId charge(Charge charge);
    void refund(PaymentId paymentId);
}
