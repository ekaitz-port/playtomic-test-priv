package com.playtomic.tests.wallet;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletRepository;
import com.playtomic.tests.wallet.domain.examples.WalletExamples;
import com.playtomic.tests.wallet.domain.examples.WalletIdExamples;
import com.playtomic.tests.wallet.infrastructure.api.WalletController;
import com.playtomic.tests.wallet.infrastructure.api.WalletResponse;
import com.playtomic.tests.wallet.domain.WalletId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WalletApplicationIT {

    @Autowired
    private WalletController controller;

    @Autowired
    private WalletRepository repository;

    @Test
    public void should_get_the_wallet() {
        Wallet savedWallet = WalletExamples.random();
        repository.save(savedWallet);
        WalletId id = savedWallet.id();

        WalletResponse foundWallet = controller.getWallet(id.asString());

        Assertions.assertThat(foundWallet).isNotNull();
        Assertions.assertThat(foundWallet.id()).isEqualTo(id.asString());
        Assertions.assertThat(foundWallet.balance()).isEqualTo(savedWallet.balanceAmount());
    }

    @Test
    public void should_get_not_found_if_the_wallet_does_not_exist() {
        WalletId id = WalletIdExamples.random();
        WalletResponse wallet = controller.getWallet(id.asString());

        Assertions.assertThat(wallet).isNotNull();
        Assertions.assertThat(wallet.id()).isEqualTo(id.asString());
    }
}
