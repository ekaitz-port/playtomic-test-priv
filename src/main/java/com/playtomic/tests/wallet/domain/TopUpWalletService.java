package com.playtomic.tests.wallet.domain;

import org.springframework.stereotype.Service;

@Service
public class TopUpWalletService {
    private final PaymentPlatform paymentPlatform;
    private final WalletRepository walletRepository;

    public TopUpWalletService(PaymentPlatform paymentPlatform, WalletRepository walletRepository) {
        this.paymentPlatform = paymentPlatform;
        this.walletRepository = walletRepository;
    }

    public void execute(WalletId walletId, Charge charge) {
        Wallet wallet = walletRepository.findById(walletId);
        wallet.topUp(charge.getAmount());
        PaymentId paymentId = paymentPlatform.charge(charge);
        try {
            walletRepository.save(wallet);
        } catch (SavingWalletError e) {
            paymentPlatform.refund(paymentId);
            throw new TopUpFailed(e);
        }
    }
}
