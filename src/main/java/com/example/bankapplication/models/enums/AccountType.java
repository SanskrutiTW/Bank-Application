package com.example.bankapplication.models.enums;

import java.math.BigDecimal;

public enum AccountType {
    // Define the types with their specific minimum balance requirements
    SALARY(BigDecimal.ZERO),
    SAVINGS(new BigDecimal("500.00")),
    CURRENT(new BigDecimal("5000.00")),
    FIXED_DEPOSIT(new BigDecimal("10000.00"));

    private final BigDecimal minBalance;

    // Constructor for the Enum
    AccountType(BigDecimal minBalance) {
        this.minBalance = minBalance;
    }

    // Getter to retrieve the limit in your Service
    public BigDecimal getMinBalance() {
        return minBalance;
    }
}

