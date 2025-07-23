package com.toster.service;

import com.toster.*;
import com.toster.repository.AccountStatement;
import com.toster.repository.Account;
import com.toster.repository.AccountStatementRepository;
import com.toster.repository.Amount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class MoneyOperationService {

    private final AccountStatementRepository accountStatementRepository;

    public MoneyOperationService(final AccountStatementRepository accountStatementRepository) {
        this.accountStatementRepository = accountStatementRepository;
    }

    public void depositMoney(final Account account, final Amount amount) {
        try {
            validateInputs(account, amount);
            final BigDecimal balance = accountStatementRepository.getBalance(account.id());
            final BigDecimal amountInDefaultCurrency = RateService.getInstance().getAmountInDefaultCurrency(amount);
            final BigDecimal newBalance = balance.add(amountInDefaultCurrency);
            accountStatementRepository.addAccountStatement(new AccountStatement(Operation.DEPOSIT, LocalDateTime.now(), amount, new Account(account.id(), new Amount(Properties.DEFAULT_CURRENCY, newBalance))));

        } catch (Exception e) {
            System.err.println("Error during deposit: " + e.getMessage());
        }
    }

    public void withdrawMoney(final Account account, final Amount amount) {
        try {
            final BigDecimal balance = accountStatementRepository.getBalance(account.id());
            validateInputs(account, amount);
            final BigDecimal amountInDefaultCurrency = RateService.getInstance().getAmountInDefaultCurrency(amount);
            checkBalanceForWithdraw(balance, amountInDefaultCurrency);
            final BigDecimal newBalance = balance.subtract(amountInDefaultCurrency);
            accountStatementRepository.addAccountStatement(new AccountStatement(Operation.WITHDRAW, LocalDateTime.now(), amount, new Account(account.id(), new Amount(Properties.DEFAULT_CURRENCY, newBalance))));
        } catch (Exception e) {
            System.err.println("Error during withdrawal: " + e.getMessage());
        }
    }

    public void printAccountStatements(final Account account) {
        Objects.requireNonNull(account, "Account statement cannot be null");
        final List<AccountStatement> accountStatements = accountStatementRepository.getCustomerAccountStatementsSortByRecent(account.id());
        if (accountStatements.isEmpty()) {
            System.out.println("No account statements for customer of id: " + account.id());
            return;
        }
        Utils.printAccountStatements(accountStatements);

    }

    private void validateInputs(final Account account, final Amount amount) {
        Objects.requireNonNull(account, "Account cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (amount.amountValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private void checkBalanceForWithdraw(final BigDecimal balance, final BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }
}
