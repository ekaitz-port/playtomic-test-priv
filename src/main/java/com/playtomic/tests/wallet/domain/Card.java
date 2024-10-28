package com.playtomic.tests.wallet.domain;

import lombok.Getter;

@Getter
public class Card {
    private final String number;

    public Card(String number) {
        this.number = number;
    }
}
