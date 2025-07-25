package com.toster.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AccountStatements implements AccountStatementRepository {

    private final List<AccountStatement> accountStatements = new ArrayList<>();

    @Override
    public void addAccountStatement(final AccountStatement accountStatement) {
        accountStatements.add(accountStatement);
    }

    @Override
    public List<AccountStatement> getCustomerAccountStatementsSortByRecent(final String id) {
        return accountStatements.stream()
                .filter(statement -> statement.account().id().equals(id))
                .sorted(Comparator.comparing(AccountStatement::date).reversed())
                .toList();
    }

    @Override
    public BigDecimal getBalance(final String id) {
        final List<AccountStatement> statements = getCustomerAccountStatementsSortByRecent(id);
        if (statements.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return statements.getFirst().account().balance().amountValue();
    }
}