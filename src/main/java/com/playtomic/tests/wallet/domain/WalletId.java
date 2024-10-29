package com.playtomic.tests.wallet.domain;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
public class WalletId {
    private final UUID id;

    public WalletId(String id) {
        this.id = UUID.fromString(id);
    }

    public String asString() {
        return id.toString();
    }
}
