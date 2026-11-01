package com.example.bankapi.account.dto;


import com.example.bankapi.account.enums.AccountStatus;

import java.math.BigDecimal;

public record AccountResponse(Long id, String iban, String owner,
                              BigDecimal balance, AccountStatus status) { }