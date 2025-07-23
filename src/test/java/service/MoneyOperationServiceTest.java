package service;

import com.toster.repository.Account;
import com.toster.repository.Amount;
import com.toster.Currency;
import com.toster.Properties;
import com.toster.repository.AccountStatementRepository;
import com.toster.repository.AccountStatements;
import com.toster.service.MoneyOperationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.toster.Utils.compareBigDecimalsEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyOperationServiceTest {
    private Account account;
    private MoneyOperationService service;
    private AccountStatementRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new AccountStatements();
        account = new Account();
        service = new MoneyOperationService(repository);
    }

    @Test
    public void depositMoneyIncreasesBalanceInDefaultCurrency() {
        service.depositMoney(account, new Amount(Properties.DEFAULT_CURRENCY, BigDecimal.valueOf(200)));
        assertTrue(compareBigDecimalsEquals(BigDecimal.valueOf(200), repository.getBalance(account.id())));
    }

    @Test
    public void withdrawMoneyDecreasesBalanceInDefaultCurrency() {
        service.depositMoney(account, new Amount(Properties.DEFAULT_CURRENCY, BigDecimal.valueOf(500)));
        service.withdrawMoney(account, new Amount(Properties.DEFAULT_CURRENCY, BigDecimal.valueOf(200)));
        assertTrue(compareBigDecimalsEquals(BigDecimal.valueOf(300), repository.getBalance(account.id())));
    }

    @Test
    public void depositMoneyIncreasesBalanceInUSD() {
        service.depositMoney(account, new Amount(Currency.USD, BigDecimal.valueOf(200)));
        assertTrue(compareBigDecimalsEquals(BigDecimal.valueOf(170), repository.getBalance(account.id())));
    }


    @Test
    public void withdrawMoneyDecreasesBalanceInUSD() {
        service.depositMoney(account, new Amount(Currency.EUR, BigDecimal.valueOf(100)));
        service.depositMoney(account, new Amount(Currency.USD, BigDecimal.valueOf(500)));
        service.withdrawMoney(account, new Amount(Currency.USD, BigDecimal.valueOf(200)));
        assertTrue(compareBigDecimalsEquals(BigDecimal.valueOf(355), repository.getBalance(account.id())));
    }

    @Test
    public void withdrawMoreThanBalanceInUSDDoesntSave() {
        service.depositMoney(account, new Amount(Currency.EUR, BigDecimal.TEN));
        service.withdrawMoney(account, new Amount(Currency.USD, BigDecimal.valueOf(12)));
        assertEquals(BigDecimal.TEN, repository.getBalance(account.id()));
    }

    @Test
    public void depositNegativeAmountDoesntSave() {
        service.depositMoney(account, new Amount(Properties.DEFAULT_CURRENCY, BigDecimal.valueOf(-100)));
        assertEquals(BigDecimal.ZERO, repository.getBalance(account.id()));
    }

    @Test
    public void withdrawMoreThanBalanceDoesntSave() {
        service.withdrawMoney(account, new Amount(Properties.DEFAULT_CURRENCY, BigDecimal.valueOf(300)));
        assertEquals(BigDecimal.ZERO, repository.getBalance(account.id()));
    }

    @Test
    public void printAccountStatements() {
        service.depositMoney(account, new Amount(Properties.DEFAULT_CURRENCY, BigDecimal.valueOf(100)));
        service.withdrawMoney(account, new Amount(Currency.PLN, BigDecimal.valueOf(100)));
        service.withdrawMoney(account, new Amount(Currency.USD, BigDecimal.valueOf(20)));
        service.printAccountStatements(account);
        assertEquals(3, repository.getCustomerAccountStatementsSortByRecent(account.id()).size());
    }
}