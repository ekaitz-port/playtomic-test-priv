package com.playtomic.tests.wallet.infrastructure.api;

import com.playtomic.tests.wallet.domain.*;
import com.playtomic.tests.wallet.domain.examples.*;
import com.playtomic.tests.wallet.infrastructure.api.ObtainWalletController.WalletResponse;
import com.playtomic.tests.wallet.infrastructure.api.TopUpWalletController.WalletTopUpBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WalletAcceptanceTest {

    @Autowired
    private TopUpWalletController topUpController;

    @Autowired
    private ObtainWalletController obtainController;

    @Autowired
    private WalletRepository repository;

    @MockBean
    private PaymentPlatform paymentPlatform;

    @Test
    public void should_get_the_wallet() {
        Wallet savedWallet = createRandomWalletInRepository();
        String id = savedWallet.idAsString();

        WalletResponse foundWallet = obtainController.getWallet(id);

        assertThat(foundWallet).isNotNull();
        assertThat(foundWallet.id()).isEqualTo(id);
        assertThat(foundWallet.balance()).isEqualTo(savedWallet.balanceAmount());
    }

    @Test
    public void should_get_not_found_if_the_wallet_does_not_exist() {
        WalletId id = WalletIdExamples.random();
        assertThrows(WalletNotFound.class, () -> obtainController.getWallet(id.asString()));
    }

    @Test
    public void should_top_up_the_wallet() {
        Wallet savedWallet = createRandomWalletInRepository();
        BigDecimal initialAmount = savedWallet.balanceAmount();
        BigDecimal amountToAdd = AmountExamples.random();
        Card stripeCard = CardExamples.stripeSandbox();
        Charge charge = new Charge(stripeCard.getNumber(), amountToAdd);
        PaymentId randomPaymentId = PaymentIdExamples.random();

        mockCharges(charge, randomPaymentId);

        topUpController.topup(savedWallet.idAsString(), new WalletTopUpBody(stripeCard.getNumber(), amountToAdd));

        WalletResponse foundWallet = obtainController.getWallet(savedWallet.idAsString());
        assertThat(foundWallet).isNotNull();
        assertThat(foundWallet.balance()).isEqualTo(initialAmount.add(amountToAdd));
    }

    private Wallet createRandomWalletInRepository() {
        Wallet savedWallet = WalletExamples.random();
        repository.save(savedWallet);
        return savedWallet;
    }

    private void mockCharges(Charge charge, PaymentId paymentIdToReturn) {
        when(paymentPlatform.charge(charge)).thenReturn(paymentIdToReturn);
    }
}
