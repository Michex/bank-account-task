package com.toster;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class MoneyOperationService {

    public void depositMoney(final Account account, final BigDecimal amount) {
        try {
            validateInputs(account, amount);
            final BigDecimal newBalance = account.updateBalance(account.getBalance().add(amount));
            account.addAccountStatement(new AccountStatement(Operation.DEPOSIT, LocalDateTime.now(), amount, newBalance));
        } catch (Exception e) {
            System.err.println("Error during deposit: " + e.getMessage());
        }
    }

    public void withdrawMoney(final Account account, final BigDecimal amount) {
        try {
            final BigDecimal currentBalance = account.getBalance();
            validateInputs(account, amount);
            checkBalanceForWithdraw(currentBalance, amount);
            final BigDecimal newBalance = account.updateBalance(currentBalance.subtract(amount));
            account.addAccountStatement(new AccountStatement(Operation.WITHDRAW, LocalDateTime.now(), amount, newBalance));
        } catch (Exception e) {
            System.err.println("Error during withdrawal: " + e.getMessage());
        }
    }

    public void printAccountStatements(final Account account) {
        Objects.requireNonNull(account, "Account statement cannot be null");
        account.getAccountStatementsAsPrintable().forEach(System.out::println);
    }

    private void validateInputs(final Account account, final BigDecimal amount) {
        Objects.requireNonNull(account, "Account cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private void checkBalanceForWithdraw(final BigDecimal balance, final BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }
}
