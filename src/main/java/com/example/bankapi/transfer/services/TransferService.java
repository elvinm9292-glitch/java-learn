package com.example.bankapi.transfer.services;

import com.example.bankapi.account.entity.Account;
import com.example.bankapi.account.service.AccountService;
import com.example.bankapi.ledger.service.LedgerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferService {
    private final AccountService accountService;
    private final LedgerService ledgerService;

    public TransferService(AccountService accountService, LedgerService ledgerService){
        this.accountService = accountService;
        this.ledgerService = ledgerService;
    }

    @Transactional
    public Long transfer(String fromIban, String toIban, BigDecimal amount){
        if (fromIban.equals(toIban)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        Account from = accountService.getByIban(fromIban);
        Account to = accountService.getByIban(toIban);
        from.withdraw(amount);
        to.deposit(amount);
        return ledgerService.recordTransfer(from, to, amount);
    }
}
