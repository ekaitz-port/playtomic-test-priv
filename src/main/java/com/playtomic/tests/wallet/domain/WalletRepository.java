package com.playtomic.tests.wallet.domain;

public interface WalletRepository {
    Wallet findById(WalletId id);
    void save(Wallet wallet);
}
