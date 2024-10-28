package com.playtomic.tests.wallet.api;

import java.math.BigDecimal;

public record WalletResponse(String id, BigDecimal balance) {
}
