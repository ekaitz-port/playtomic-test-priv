package com.playtomic.tests.wallet.domain;

import com.playtomic.tests.wallet.domain.examples.ChargeExamples;
import com.playtomic.tests.wallet.domain.examples.PaymentIdExamples;
import com.playtomic.tests.wallet.domain.examples.WalletExamples;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TopUpWalletServiceTest {

    private final PaymentPlatform paymentPlatform = mock(PaymentPlatform.class);
    private final WalletRepository walletRepository = mock(WalletRepository.class);
    private final TopUpWalletService topUpWalletService = new TopUpWalletService(paymentPlatform, walletRepository);

    @Test
    public void should_top_up_wallet() {
        Wallet wallet = WalletExamples.random();
        WalletId walletId = new WalletId(wallet.idAsString());
        Charge charge = ChargeExamples.random();
        BigDecimal expectedAmount = wallet.balanceAmount().add(charge.getAmount());
        Wallet expectedWallet = new Wallet(walletId, new Balance(expectedAmount));

        when(walletRepository.findById(walletId)).thenReturn(wallet);

        topUpWalletService.execute(walletId, charge);

        verify(paymentPlatform).charge(charge);
        verify(walletRepository).save(expectedWallet);
    }

    @Test
    public void should_refund_the_payment_if_save_fails() throws Exception {
        Wallet wallet = WalletExamples.random();
        WalletId walletId = new WalletId(wallet.idAsString());
        Charge charge = ChargeExamples.random();
        PaymentId paymentId = PaymentIdExamples.random();

        when(walletRepository.findById(walletId)).thenReturn(wallet);
        when(paymentPlatform.charge(charge)).thenReturn(paymentId);
        doThrow(new SavingWalletError(new Exception("db error"))).when(walletRepository).save(any(Wallet.class));

        assertThrows(TopUpFailed.class, () -> topUpWalletService.execute(walletId, charge));

        verify(paymentPlatform).charge(charge);
        verify(paymentPlatform).refund(paymentId);
    }
}