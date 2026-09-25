package com.example.bankapi.entity;

import com.example.bankapi.enums.AccountStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account", schema = "java_learn")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String iban;
    private String owner;
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "account")
    private List<Card> cards = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status = AccountStatus.ACTIVE;

    protected Account() {
    }

    public Account(String iban, String owner) {
        this.iban = iban;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getIban() {
        return iban;
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountStatus getStatus(){
        return status;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void block(){
        if (status != AccountStatus.ACTIVE){
            throw new IllegalStateException("Счет уже заблокирован");
        }
        this.status = AccountStatus.BLOCKED;
    }

    public void deposit(BigDecimal amount){
        if (status != AccountStatus.ACTIVE){
            throw new IllegalStateException("Счет заблокирован");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Сумма пополнения должна быть больше нуля");
        }
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount){
        if (status != AccountStatus.ACTIVE){
            throw new IllegalStateException("Счет заблокирован");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Сумма снятия должна быть больше нуля");
        }

        if(balance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }

    }
}
