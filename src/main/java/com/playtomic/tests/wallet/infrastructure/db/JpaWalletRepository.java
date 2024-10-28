package com.playtomic.tests.wallet.infrastructure.db;

import org.springframework.data.repository.CrudRepository;

interface JpaWalletRepository extends CrudRepository<WalletEntity, String> {
}
