package com.playtomic.tests.wallet.domain;

import lombok.NonNull;

import java.util.UUID;

public class WalletId {
    @NonNull
    private UUID id;

    public WalletId(UUID id) {
        this.id = id;
    }

    public String asString() {
        return id.toString();
    }
}
