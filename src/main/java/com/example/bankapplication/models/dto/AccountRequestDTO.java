package com.example.bankapplication.models.dto;

import com.example.bankapplication.models.enums.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountRequestDTO(
        @Schema(description="Unique bank account number", example="123456789012345")
        @NotBlank(message="AccountNumber is required")
        @Size(max=20)
        @Pattern(regexp="^[0-9]{10,20}$", message="Account Number must be between 10 and 20 digits")
        String accountNumber,
        @Schema(description="Primary Key ID of the customer owning this account", example="1")
        @NotNull(message="CustomerId is required")
        Long customerId,
        @Schema(description="Type of account (SAVINGS, CURRENT,etc.",example="SAVINGS")
        @NotNull(message="AccountType is required")
        AccountType accountType,
        @Schema(description="Initial deposit amount", example="5000.0")
        @NotNull(message="Balance is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
        BigDecimal balance,
        @Schema(description = "Indian Financial System Code", example = "ICIC0001234")
        @NotBlank(message = "IFSC Code is required")
        @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC format (e.g., ICIC0001234)")
        String ifscCode,
        @Schema(description = "Physical branch location name", example = "Mumbai Main Branch")
        @NotBlank(message = "Branch name is required")
        @Size(min = 2, max = 100, message = "Branch name must be between 2 and 100 characters")
        String branch
) { }
