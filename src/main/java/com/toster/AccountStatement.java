package com.toster;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountStatement(Operation accountOperation, LocalDateTime date, BigDecimal amount, BigDecimal balance) {
}
