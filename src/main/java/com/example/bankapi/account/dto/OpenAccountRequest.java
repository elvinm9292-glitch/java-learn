package com.example.bankapi.account.dto;

import com.example.bankapi.common.validation.ValidIban;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OpenAccountRequest(
        @Schema(description = "Account IBAN", example = "AZ21NABZ00000000137010001944")
        @NotBlank(message = "IBAN is required")
        @ValidIban
        String iban,

        @Schema(description = "Account owner full name", example = "Kamran Akhmedov")
        @NotBlank(message = "Owner is required")
        @Size(max = 200, message = "Owner must be at most 200 characters")
        String owner
) { }

