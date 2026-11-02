package com.example.bankapi.ledger.entity;

import com.example.bankapi.account.entity.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ledger_entry", schema = "java_learn")
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operation_id", nullable = false)
    private Operation operation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    protected LedgerEntry() {
    }

    // Only Operation creates entries, so an entry never exists outside an operation
    LedgerEntry(Operation operation, Account account, BigDecimal amount) {
        this.operation = operation;
        this.account = account;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Operation getOperation() {
        return operation;
    }

    public Account getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
