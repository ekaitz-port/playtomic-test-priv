package com.playtomic.tests.wallet.infrastructure.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public class Payment {

    @NonNull
    private String id;

    @JsonCreator
    public Payment(@JsonProperty(value = "id", required = true) String id) {
        this.id = id;
    }
}