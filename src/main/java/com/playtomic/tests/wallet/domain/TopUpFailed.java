package com.playtomic.tests.wallet.domain;

public class TopUpFailed extends RuntimeException {
    public TopUpFailed(Exception e) {
        super("The top up was canceled, due to an unexpected error", e);
    }
}
