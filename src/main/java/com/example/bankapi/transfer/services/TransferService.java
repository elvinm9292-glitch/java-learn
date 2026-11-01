package com.example.bankapi.transfer.services;

import com.example.bankapi.account.dto.AmountRequest;
import com.example.bankapi.account.entity.Account;
import com.example.bankapi.account.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferService {
    private final AccountService accountService;

    public TransferService(AccountService accountService){
        this.accountService = accountService;
    }

    @Transactional
    public void transfer(String fromIban, String toIban, BigDecimal amount){
        if (fromIban.equals(toIban)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        Account from = accountService.getByIban(fromIban);
        Account to = accountService.getByIban(toIban);
        from.withdraw(amount);
        to.deposit(amount);
    }
}