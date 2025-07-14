package com.toster;

public enum Operation {

    DEPOSIT("Deposit"),
    WITHDRAW("Withdraw");

    private final String description;

    Operation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
