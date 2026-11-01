package com.example.bankapi.card.dto;

import com.example.bankapi.card.enums.CardTierType;
import com.example.bankapi.common.validation.ValidIban;
import jakarta.validation.constraints.NotNull;

public record IssueCardRequest(
        @ValidIban
        String iban,
        @NotNull(message = "Card tier is required") CardTierType tier) { }