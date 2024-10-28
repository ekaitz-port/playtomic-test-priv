package com.playtomic.tests.wallet;

import com.playtomic.tests.wallet.domain.WalletId;

import java.util.UUID;

public class WalletIdExamples {
    public static WalletId random() {
        return new WalletId(UUID.randomUUID());
    }
}
