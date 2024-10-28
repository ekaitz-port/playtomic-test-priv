package com.playtomic.tests.wallet;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;

import java.util.Random;
import java.util.UUID;

public class WalletExamples {
    public static Wallet random() {
        return new Wallet(WalletIdExamples.random(), BalanceExamples.random());
    }
}
