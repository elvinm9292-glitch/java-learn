package com.example.bankapi.account.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AmountRequest(
        @NotNull
        @Positive(message = "aaa")
        @Digits(integer = 17, fraction = 2)
        BigDecimal amount) { }
