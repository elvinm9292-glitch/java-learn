package com.example.bankapi.account.exception;

public class AccountNotFoundException  extends RuntimeException{

    public AccountNotFoundException(Long id){
        super("Счет не найден: " + id);
    }

    public AccountNotFoundException(String iban){
        super("Счет не найден: " + iban);
    }
}
