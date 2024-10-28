package com.playtomic.tests.wallet.application;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletNotFound;
import com.playtomic.tests.wallet.domain.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObtainWallet {
    private final WalletRepository repository;

    @Autowired
    public ObtainWallet(WalletRepository repository) {
        this.repository = repository;
    }

    public Wallet obtain(WalletId id) {
        return repository.findById(id);
    }
}
