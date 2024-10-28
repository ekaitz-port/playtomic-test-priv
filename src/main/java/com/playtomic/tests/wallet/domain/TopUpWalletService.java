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

    public void process(WalletId walletId, Charge charge) {
        paymentPlatform.process(charge);
        Wallet wallet = walletRepository.findById(walletId);
        wallet.topUp(charge.getAmount());
        walletRepository.save(wallet);
    }
}
