package com.example.bankapi.card.exception;

public class DuplicateCardNumberException extends RuntimeException{
    public DuplicateCardNumberException(){
        super("Could not generate a unique card number");
    }
}
