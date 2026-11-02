package com.example.bankapi.ledger.entity;

import com.example.bankapi.account.entity.Account;
import com.example.bankapi.ledger.enums.OperationType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operation", schema = "java_learn")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OperationType type;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // Entries are saved together with the operation
    @OneToMany(mappedBy = "operation", cascade = CascadeType.PERSIST)
    private List<LedgerEntry> entries = new ArrayList<>();

    protected Operation() {
    }

    private Operation(OperationType type) {
        this.type = type;
        this.createdAt = Instant.now();
    }

    public static Operation transfer(Account from, Account to, BigDecimal amount) {
        Operation operation = new Operation(OperationType.TRANSFER);
        operation.addEntry(from, amount.negate());
        operation.addEntry(to, amount);
        operation.requireBalanced();
        return operation;
    }

    private void addEntry(Account account, BigDecimal amount) {
        entries.add(new LedgerEntry(this, account, amount));
    }

    // Money is neither created nor lost: entries of one operation must sum to zero
    private void requireBalanced() {
        BigDecimal sum = BigDecimal.ZERO;
        for (LedgerEntry entry : entries) {
            sum = sum.add(entry.getAmount());
        }
        if (sum.signum() != 0) {
            throw new IllegalStateException("Operation entries must sum to zero, got " + sum);
        }
    }

    public Long getId() {
        return id;
    }

    public OperationType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<LedgerEntry> getEntries() {
        return List.copyOf(entries);
    }
}
