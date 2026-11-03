package com.example.bankapi.account.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Insufficient funds");
    }
}
