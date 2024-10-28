package com.playtomic.tests.wallet.domain;

public class WalletNotFound extends RuntimeException {
    public WalletNotFound(WalletId id) {
        super("Wallet not found: " + id);
    }
}
