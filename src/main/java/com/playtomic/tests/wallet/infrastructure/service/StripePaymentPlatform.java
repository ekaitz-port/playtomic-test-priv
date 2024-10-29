package com.playtomic.tests.wallet.infrastructure.service;

import com.playtomic.tests.wallet.domain.Charge;
import com.playtomic.tests.wallet.domain.PaymentId;
import com.playtomic.tests.wallet.domain.PaymentPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentPlatform implements PaymentPlatform {

    private final StripeService stripe;

    @Autowired
    public StripePaymentPlatform(StripeService stripe) {
        this.stripe = stripe;
    }

    @Override
    public PaymentId charge(Charge charge) {
        return new PaymentId(stripe.charge(charge.cardNumber(), charge.getAmount()).getId());
    }

    @Override
    public void refund(PaymentId paymentId) {
        stripe.refund(paymentId.value());
    }
}
