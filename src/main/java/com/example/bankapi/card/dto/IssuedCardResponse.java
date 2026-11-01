package com.example.bankapi.card.dto;

import com.example.bankapi.card.enums.CardStatus;
import com.example.bankapi.card.enums.CardTierType;

import java.time.YearMonth;

public record IssuedCardResponse(
        Long id, String number, String cvv, YearMonth expiry,
        CardStatus status, CardTierType tier, Long accountId) { }