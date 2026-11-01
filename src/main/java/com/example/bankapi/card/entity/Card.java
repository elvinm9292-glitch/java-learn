package com.example.bankapi.card.entity;

import com.example.bankapi.account.entity.Account;
import com.example.bankapi.card.enums.CardStatus;
import com.example.bankapi.card.enums.CardTierType;
import jakarta.persistence.*;
import java.time.YearMonth;

@Entity
@Table(name = "cards", schema = "java_learn")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 16)
    private String number;

    @Column(nullable = false, length = 3)
    private String cvv;

    @Column(nullable = false, length = 7)
    private YearMonth expiry;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CardStatus status = CardStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CardTierType tier = CardTierType.CLASSIC;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    protected Card() {
    }

    public Card(Account account, String number, String cvv, YearMonth expiry, CardTierType tier) {
        this.account = account;
        this.number = number;
        this.cvv = cvv;
        this.expiry = expiry;
        this.tier = tier;
    }

    public void block() {
        if (status == CardStatus.BLOCKED) {
            throw new IllegalStateException("Card is already blocked");
        }
        this.status = CardStatus.BLOCKED;
    }

    public Long getId() { return id; }
    public YearMonth getExpiry() { return expiry; }
    public CardStatus getStatus() { return status; }
    public Account getAccount() { return account; }
    public CardTierType getTier(){
        return tier;
    }
    public String getNumber() { return number; }
    public String getMaskedNumber() { return "**** **** **** " + number.substring(12); }
    public String getCvv() { return cvv; }
}