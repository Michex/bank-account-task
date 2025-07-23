package service;

import com.toster.Currency;
import com.toster.repository.Amount;
import com.toster.service.RateService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.toster.Utils.compareBigDecimalsEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateServiceTest {

    private final RateService rateService = RateService.getInstance();

    @Test
    public void returnsAmountInDefaultCurrencyWhenCurrencyIsDefault() {
        final Amount amount = new Amount(Currency.EUR, BigDecimal.valueOf(100));
        assertTrue(compareBigDecimalsEquals(BigDecimal.valueOf(100), rateService.getAmountInDefaultCurrency(amount)));
    }

    @Test
    public void convertsAmountToDefaultCurrencyWhenCurrencyIsUsd() {
        final Amount amount = new Amount(Currency.USD, BigDecimal.valueOf(100));
        assertTrue(compareBigDecimalsEquals(BigDecimal.valueOf(85), rateService.getAmountInDefaultCurrency(amount)));
    }

    @Test
    public void convertsAmountToDefaultCurrencyWhenCurrencyIsPln() {
        final Amount amount = new Amount(Currency.PLN, BigDecimal.valueOf(100));
        assertTrue(compareBigDecimalsEquals(BigDecimal.valueOf(24), rateService.getAmountInDefaultCurrency(amount)));
    }
}
