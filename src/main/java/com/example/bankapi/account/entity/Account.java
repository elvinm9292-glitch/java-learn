package com.example.bankapi.account.entity;

import com.example.bankapi.account.enums.AccountStatus;
import com.example.bankapi.account.exception.InsufficientFundsException;
import com.example.bankapi.card.entity.Card;
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

    @Column(nullable = false, unique = true)
    private String iban;

    @Column(nullable = false, length = 200)
    private String owner;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "account")
    private List<Card> cards = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status = AccountStatus.ACTIVE;

    @Version
    @Column(nullable = false)
    private Long version;
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
        validateAmount(amount);
        if (status != AccountStatus.ACTIVE){
            throw new IllegalStateException("Счет заблокирован");
        }
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount){
        validateAmount(amount);
        if (status != AccountStatus.ACTIVE){
            throw new IllegalStateException("Счет заблокирован");
        }
        if (balance.compareTo(amount) < 0){
            throw new InsufficientFundsException();
        }
        balance = balance.subtract(amount);
    }

    private void validateAmount(BigDecimal amount){
        if (amount == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount.stripTrailingZeros().scale() > 2) {
            throw new IllegalArgumentException("Amount must have at most 2 decimal places");
        }
    }
}
