package com.example.bankapi.account.dto;

import com.example.bankapi.common.validation.ValidIban;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OpenAccountRequest(
        @Schema(description = "Account IBAN", example = "AZ21NABZ00000000137010001944")
        @NotBlank(message = "IBAN is required")
        @ValidIban
        String iban,

        @Schema(description = "Account owner full name", example = "Kamran Akhmedov")
        @NotBlank(message = "Owner is required")
        String owner
) { }

