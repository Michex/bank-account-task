package com.toster.repository;

import java.math.BigDecimal;
import java.util.List;

public interface AccountStatementRepository {
    void addAccountStatement(final AccountStatement accountStatement);

    List<AccountStatement> getCustomerAccountStatementsSortByRecent(final String id);

    BigDecimal getBalance(final String accountId);

}
