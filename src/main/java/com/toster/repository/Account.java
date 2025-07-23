package com.toster.repository;

import com.toster.Properties;

import java.math.BigDecimal;
import java.util.UUID;

public record Account(String id, Amount balance) {
    public Account() {
        this(UUID.randomUUID().toString(), new Amount(Properties.DEFAULT_CURRENCY, BigDecimal.ZERO));
    }
}