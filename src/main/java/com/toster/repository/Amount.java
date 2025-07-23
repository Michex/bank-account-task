package com.toster.repository;

import com.toster.Currency;

import java.math.BigDecimal;

public record Amount(Currency currency, BigDecimal amountValue) {

    @Override
    public String toString() {
        return String.format("%s %s", amountValue, currency.toString());
    }
}
