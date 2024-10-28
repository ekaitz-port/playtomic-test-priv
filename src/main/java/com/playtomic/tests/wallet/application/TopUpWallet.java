package com.playtomic.tests.wallet.application;

import com.playtomic.tests.wallet.domain.Charge;
import com.playtomic.tests.wallet.domain.TopUpWalletService;
import com.playtomic.tests.wallet.domain.WalletId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopUpWallet {
    private final TopUpWalletService service;

    @Autowired
    public TopUpWallet(TopUpWalletService service) {
        this.service = service;
    }

    public void topUp(WalletId walletId, Charge charge) {
        service.process(walletId, charge);
    }
}
