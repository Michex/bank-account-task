package com.toster;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Account {

    private final List<AccountStatement> accountStatements = new ArrayList<>();
    private BigDecimal balance = BigDecimal.ZERO;

    public void addAccountStatement(final AccountStatement accountStatement) {
        Objects.requireNonNull(accountStatement, "Account statement cannot be null");
        accountStatements.add(accountStatement);
    }

    public List<String> getAccountStatementsAsPrintable() {
        return accountStatements.stream()
                .sorted(Comparator.comparing(AccountStatement::date).reversed())
                .map(statement -> String.format("%s %s at %s, Balance: %s",
                        statement.accountOperation().getDescription(),
                        statement.amount(),
                        statement.date(),
                        statement.balance()))
                .toList();
    }

    public BigDecimal updateBalance(final BigDecimal newBalance) {
        Objects.requireNonNull(newBalance, "New balance cannot be null");
        this.balance = newBalance;
        return balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}
