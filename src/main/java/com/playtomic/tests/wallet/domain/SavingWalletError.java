package com.playtomic.tests.wallet.domain;

public class SavingWalletError extends RuntimeException {
    public SavingWalletError(Exception e) {
        super("Error saving the wallet in database", e);
    }
}
