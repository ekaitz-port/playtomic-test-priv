package com.playtomic.tests.wallet.domain;

public interface PaymentPlatform {
    void process(Charge charge);
}
