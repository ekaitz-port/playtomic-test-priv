package com.playtomic.tests.wallet.infrastructure.db;

import com.playtomic.tests.wallet.domain.Balance;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringDataWalletRepository implements WalletRepository {
    private final JpaWalletRepository jpaRepository;

    @Autowired
    SpringDataWalletRepository(JpaWalletRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Wallet> findById(WalletId id) {
        return jpaRepository
                .findById(id.asString())
                .map(entity -> new Wallet(new WalletId(entity.getId()), new Balance(entity.getBalance())));
    }

    private Wallet toWallet(WalletEntity entity) {
        return null;
    }

    @Override
    public void save(Wallet wallet) {

    }
}
