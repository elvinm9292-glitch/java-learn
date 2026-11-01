package com.example.bankapi.card.exception;

public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException(Long id){
        super("Card not found: " + id);
    }
}
