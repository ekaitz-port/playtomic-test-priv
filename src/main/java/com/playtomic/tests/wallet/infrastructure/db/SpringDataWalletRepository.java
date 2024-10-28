package com.playtomic.tests.wallet.infrastructure.db;

import com.playtomic.tests.wallet.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SpringDataWalletRepository implements WalletRepository {
    private final JpaWalletRepository jpaRepository;

    @Autowired
    SpringDataWalletRepository(JpaWalletRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Wallet findById(WalletId id) {
        return jpaRepository
                .findById(id.asString())
                .map(entity -> new Wallet(new WalletId(entity.getId()), new Balance(entity.getBalance())))
                .orElseThrow(() -> new WalletNotFound(id));
    }

    @Override
    public void save(Wallet wallet) {
        jpaRepository.save(new WalletEntity(wallet.idAsString(), wallet.balanceAmount()));
    }
}
