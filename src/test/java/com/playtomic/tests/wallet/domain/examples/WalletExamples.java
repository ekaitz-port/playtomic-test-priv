package com.playtomic.tests.wallet.domain.examples;

import com.playtomic.tests.wallet.BalanceExamples;
import com.playtomic.tests.wallet.domain.Wallet;

public class WalletExamples {
    public static Wallet random() {
        return new Wallet(WalletIdExamples.random(), BalanceExamples.random());
    }
}
