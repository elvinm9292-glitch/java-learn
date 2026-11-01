package com.example.bankapi.account.service;


import com.example.bankapi.account.entity.Account;
import com.example.bankapi.account.exception.AccountNotFoundException;
import com.example.bankapi.account.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private  final AccountRepository accountRepository;

    public AccountService(AccountRepository repository){
        accountRepository = repository;
    }

    @Transactional(readOnly = true)
    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Long open(String iban, String owner){
        var account =  new Account(iban, owner);
        return accountRepository.save(account).getId();
    }

    public Account getById(Long id){
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Account> getAllWithCards() {
        return accountRepository.findAllWithCards();
    }

    public Account getByIban(String iban) { return accountRepository.findByIban(iban).orElseThrow(() -> new AccountNotFoundException(iban)); }

    @Transactional
    public void deposit(Long id, BigDecimal amount) {
        getById(id).deposit(amount);
    }

    @Transactional
    public void withdraw(Long id, BigDecimal amount) {
        getById(id).withdraw(amount);
    }

    @Transactional
    public void block(Long id) {
        getById(id).block();
    }
}
