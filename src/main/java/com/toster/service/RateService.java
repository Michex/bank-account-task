package com.toster.service;

import com.toster.repository.Amount;
import com.toster.Currency;

import java.math.BigDecimal;
import java.util.Objects;

import static com.toster.Properties.DEFAULT_CURRENCY;

public class RateService {

    private static final RateService INSTANCE = new RateService();

    private RateService() {}

    public static RateService getInstance() {
        return INSTANCE;
    }

    public BigDecimal getAmountInDefaultCurrency(final Amount amount) {
        Objects.requireNonNull(amount, "Amount must not be null");
        if (amount.currency().equals(DEFAULT_CURRENCY)) {
            return amount.amountValue();
        }
        return calculateAmount(amount);
    }

    private BigDecimal calculateAmount(final Amount amount) {
        return amount.amountValue().multiply(getRateRelativeToEur(amount.currency()));
    }

    private BigDecimal getRateRelativeToEur(Currency currency) {
        return switch (currency) {
            case USD -> BigDecimal.valueOf(0.85);
            case PLN -> BigDecimal.valueOf(0.24);
            default -> BigDecimal.ONE;
        };
    }
}
