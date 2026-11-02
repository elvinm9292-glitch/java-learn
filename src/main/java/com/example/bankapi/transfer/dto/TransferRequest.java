package com.example.bankapi.transfer.dto;

import com.example.bankapi.common.validation.ValidIban;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(
        @Schema(example = "AZ21NABZ00000000137010001944")
        @NotBlank(message = "Sender IBAN is required")
        @ValidIban
        String fromIban,

        @Schema(example = "AZ21NABZ00000000137010001945")
        @NotBlank(message = "Recipient IBAN is required")
        @ValidIban
        String toIban,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        @Digits(integer = 17, fraction = 2, message = "Amount must have at most 2 decimal places")
        BigDecimal amount
) { }
