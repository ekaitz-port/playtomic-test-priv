package com.playtomic.tests.wallet.domain;

import com.playtomic.tests.wallet.domain.examples.AmountExamples;
import com.playtomic.tests.wallet.domain.examples.WalletExamples;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WalletTest {
    @Test
    public void should_topup_wallet() {
        Wallet wallet = WalletExamples.random();
        BigDecimal initialAmount = wallet.balanceAmount();
        BigDecimal amountToIncrease = AmountExamples.randomGreaterThan5();

        wallet.topUp(amountToIncrease);

        assertThat(wallet.balanceAmount()).isEqualTo(initialAmount.add(amountToIncrease));
    }

    @Test
    public void should_throw_an_exception_toppingup_wallet_with_less_than_5() {
        Wallet wallet = WalletExamples.random();
        BigDecimal amountToIncrease = AmountExamples.randomLessThan5();

        assertThrows(TopUpAmountLessThanMinimum.class, () -> wallet.topUp(amountToIncrease));
    }
}