package com.toster.repository;

import com.toster.Operation;

import java.time.LocalDateTime;

public record AccountStatement(Operation accountOperation, LocalDateTime date, Amount amount, Account account) {
}
