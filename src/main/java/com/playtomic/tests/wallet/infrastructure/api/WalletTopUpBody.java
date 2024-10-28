package com.playtomic.tests.wallet.infrastructure.api;

import java.math.BigDecimal;

public record WalletTopUpBody(String card, BigDecimal amount) {
}
