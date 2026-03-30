package com.example.bankapplication.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record CustomerRequestDTO (
        @Schema(description="customer's legal first name", example="John")
        @NotBlank(message="First name is required")
        @Size(min=2,max=50)
        String firstName,
        @Schema(description="Customer's legal last name", example="Doe")
        @NotBlank(message = "Last name is requires")
        String lastName,
        @Schema(description="Unique Email address for communication", example="john.doe@gmail.com")
        @Email(message="Please provide a valid email address")
        @NotBlank(message="Email is Required")
        String email,
        @Schema(description = "Phone number with optional country code", example = "+919876543210")
        @NotBlank(message="Phone number is required")
        @Pattern(regexp="^\\+?[0-9]{10,15}$", message="Invalid phone number format")
        String phoneNumber,
        @Schema(description = "Full residential address", example = "123, Baker Street, London")
        @NotBlank(message = "Address is required")
        String address
){ }
