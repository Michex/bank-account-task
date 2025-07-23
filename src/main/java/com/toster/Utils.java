package com.toster;

import com.toster.repository.AccountStatement;

import java.math.BigDecimal;
import java.util.List;

public class Utils {

    public static void  printAccountStatements(final List<AccountStatement> accountStatements) {
        accountStatements.forEach(statement -> System.out.printf("%s %s at %s, Balance: %s%n",
            statement.accountOperation().getDescription(),
                statement.amount(),
                        statement.date(),
                        statement.account().balance().amountValue()));
    }

    public static boolean compareBigDecimalsEquals(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) == 0;
    }

}