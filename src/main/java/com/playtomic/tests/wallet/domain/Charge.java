package com.playtomic.tests.wallet.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Charge {
    private final Card card;
    private final BigDecimal amount;

    public Charge(String card, BigDecimal amount) {
        this.card = new Card(card);
        this.amount = amount;
    }

    public String cardNumber() {
        return card.number();
    }
}
