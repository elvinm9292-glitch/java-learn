package com.example.bankapi.ledger.service;

import com.example.bankapi.account.entity.Account;
import com.example.bankapi.ledger.entity.Operation;
import com.example.bankapi.ledger.repository.OperationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LedgerService {

    private final OperationRepository operationRepository;

    public LedgerService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    // Must be called inside the caller's transaction, so balances and the journal are saved together
    public Long recordTransfer(Account from, Account to, BigDecimal amount) {
        return operationRepository.save(Operation.transfer(from, to, amount)).getId();
    }
}
