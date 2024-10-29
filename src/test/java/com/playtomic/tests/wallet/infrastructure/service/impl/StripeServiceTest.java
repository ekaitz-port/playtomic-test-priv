package com.playtomic.tests.wallet.infrastructure.service.impl;


import com.playtomic.tests.wallet.domain.examples.PaymentIdExamples;
import com.playtomic.tests.wallet.infrastructure.service.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.infrastructure.service.StripeService;
import com.playtomic.tests.wallet.infrastructure.service.StripeServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.net.URI;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

/**
 * This test is failing with the current implementation.
 * <p>
 * How would you test this?
 */
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class StripeServiceTest {

    private ClientAndServer mockServer;
    URI testUri = URI.create("http://localhost:3333");
    StripeService s = new StripeService(testUri, testUri, new RestTemplateBuilder());

    @BeforeEach
    public void setUp() {
        mockServer = ClientAndServer.startClientAndServer(3333);
    }

    @AfterEach
    public void tearDown() {
        mockServer.close();
    }

    @Test
    public void test_exception() {
        String cardNumber = "4242 4242 4242 4242";
        BigDecimal amount = new BigDecimal(5);
        mockUnprocessableEntity(cardNumber, amount);
        Assertions.assertThrows(StripeAmountTooSmallException.class, () -> {
            s.charge(cardNumber, amount);
        });
    }

    @Test
    public void test_ok() throws StripeServiceException {
        String cardNumber = "4242 4242 4242 4242";
        BigDecimal amount = new BigDecimal(15);
        mockOkResponse(cardNumber, amount);
        s.charge(cardNumber, amount);
    }

    private void mockUnprocessableEntity(String card, BigDecimal amount) {
        new MockServerClient("localhost", 3333)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/")
                                .withHeader("Content-Type", "application/json")
                                .withBody(json("{ \"credit_card\": \"" + card + "\", \"amount\": " + amount + " }"))
                )
                .respond(
                        response()
                                .withStatusCode(422)
                                .withHeader("Content-Type", "application/json")
                );
    }

    private void mockOkResponse(String card, BigDecimal amount) {
        String responseBody = "{\"id\": \"" + PaymentIdExamples.random().value() + "\"}";
        new MockServerClient("localhost", 3333)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/")
                                .withHeader("Content-Type", "application/json")
                                .withBody(json("{ \"credit_card\": \"" + card + "\", \"amount\": " + amount + " }"))
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(responseBody)
                );
    }
}
