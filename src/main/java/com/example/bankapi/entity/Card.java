package com.example.bankapi.entity;

import com.example.bankapi.enums.CardStatus;
import jakarta.persistence.*;
import java.time.YearMonth;

@Entity
@Table(name = "cards", schema = "java_learn")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "masked_number", nullable = false, length = 19)
    private String maskedNumber;

    @Column(nullable = false, length = 7)
    private YearMonth expiry;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CardStatus status = CardStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    protected Card() {
    }

    public Card(Account account, String maskedNumber, YearMonth expiry) {
        this.account = account;
        this.maskedNumber = maskedNumber;
        this.expiry = expiry;
    }

    public void block() {
        if (status == CardStatus.BLOCKED) {
            throw new IllegalStateException("Карта уже заблокирована");
        }
        this.status = CardStatus.BLOCKED;
    }

    public Long getId() { return id; }
    public String getMaskedNumber() { return maskedNumber; }
    public YearMonth getExpiry() { return expiry; }
    public CardStatus getStatus() { return status; }
    public Account getAccount() { return account; }
}