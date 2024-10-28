package com.playtomic.tests.wallet;

import com.playtomic.tests.wallet.api.WalletController;
import com.playtomic.tests.wallet.api.WalletResponse;
import com.playtomic.tests.wallet.domain.WalletId;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WalletApplicationIT {

    @Inject
    WalletController controller;

    @Test
    public void should_get_the_wallet() {
        WalletId id = WalletIdExamples.random();
        WalletResponse wallet = controller.getWallet(id.asString());

        Assertions.assertThat(wallet).isNotNull();
        Assertions.assertThat(wallet.id()).isEqualTo(id.asString());
    }
}
