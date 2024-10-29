package com.playtomic.tests.wallet.infrastructure.api;

import com.playtomic.tests.wallet.domain.*;
import com.playtomic.tests.wallet.domain.examples.*;
import com.playtomic.tests.wallet.infrastructure.api.ObtainWalletController.WalletResponse;
import com.playtomic.tests.wallet.infrastructure.api.TopUpWalletController.WalletTopUpBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WalletIntegrationTest {

    @Autowired
    private TopUpWalletController topUpController;

    @Autowired
    private ObtainWalletController obtainController;

    @Autowired
    private WalletRepository repository;

    private ClientAndServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = ClientAndServer.startClientAndServer(9999);
    }

    @AfterEach
    public void tearDown() {
        mockServer.close();
    }

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
        PaymentId randomPaymentId = PaymentIdExamples.random();

        mockCharges(stripeCard, amountToAdd, randomPaymentId);

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

    private void mockCharges(Card card, BigDecimal amount, PaymentId paymentIdResponse) {
        String responseBody = "{\"id\": \"" + paymentIdResponse.value() + "\"}";
        List<Parameter> parameters = new ArrayList<>();

        parameters.add(new Parameter("credit_card", card.getNumber()));
        parameters.add(new Parameter("amount", amount.toString()));

        new MockServerClient("localhost", 9999)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/")
                                .withHeader("Content-Type", "application/json")
                                .withBody(json("{ \"credit_card\": \"" + card.getNumber() + "\", \"amount\": " + amount + " }"))
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(responseBody)
                );
    }
}
