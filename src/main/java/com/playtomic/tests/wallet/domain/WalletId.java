package com.playtomic.tests.wallet.domain;

import java.util.UUID;

public class WalletId {
    private final UUID id;

    public WalletId(String id) {
        this.id = UUID.fromString(id);
    }

    public String asString() {
        return id.toString();
    }
}
