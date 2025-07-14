import com.toster.Account;
import com.toster.MoneyOperationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyOperationServiceTest {
    private Account account;
    private MoneyOperationService service;

    @BeforeEach
    public void setUp() {
        account = new Account();
        service = new MoneyOperationService();
    }

    @Test
    public void depositMoneyIncreasesBalance() {
        service.depositMoney(account, BigDecimal.valueOf(200));
        assertEquals(BigDecimal.valueOf(200), account.getBalance());
    }

    @Test
    public void withdrawMoneyDecreasesBalance() {
        service.depositMoney(account, BigDecimal.valueOf(500));
        service.withdrawMoney(account, BigDecimal.valueOf(200));
        assertEquals(BigDecimal.valueOf(300), account.getBalance());
    }

    @Test
    public void depositNegativeAmountDoesntSave() {
        service.depositMoney(account, BigDecimal.valueOf(-100));
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    public void withdrawMoreThanBalanceDoesntSave() {
        service.withdrawMoney(account, BigDecimal.valueOf(300));
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    public void printAccountStatements() {
        service.depositMoney(account, BigDecimal.valueOf(100));
        service.withdrawMoney(account, BigDecimal.valueOf(50));
        service.printAccountStatements(account);
        assertEquals(2, account.getAccountStatementsAsPrintable().size());
        assertEquals(BigDecimal.valueOf(50), account.getBalance());
    }
}