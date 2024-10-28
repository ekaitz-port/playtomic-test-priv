package com.playtomic.tests.wallet.infrastructure.api;

import java.math.BigDecimal;

public record WalletResponse(String id, BigDecimal balance) {
}
