package com.playtomic.tests.wallet;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletNotFound;
import com.playtomic.tests.wallet.domain.WalletRepository;
import com.playtomic.tests.wallet.domain.examples.WalletExamples;
import com.playtomic.tests.wallet.domain.examples.WalletIdExamples;
import com.playtomic.tests.wallet.infrastructure.api.WalletController;
import com.playtomic.tests.wallet.infrastructure.api.WalletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        String id = savedWallet.idAsString();

        WalletResponse foundWallet = controller.getWallet(id);

        assertThat(foundWallet).isNotNull();
        assertThat(foundWallet.id()).isEqualTo(id);
        assertThat(foundWallet.balance()).isEqualTo(savedWallet.balanceAmount());
    }

    @Test
    public void should_get_not_found_if_the_wallet_does_not_exist() {
        WalletId id = WalletIdExamples.random();
        assertThrows(WalletNotFound.class, () -> controller.getWallet(id.asString()));
    }
}
