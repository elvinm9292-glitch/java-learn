package com.example.bankapi.card.service;

import com.example.bankapi.account.entity.Account;
import com.example.bankapi.account.enums.AccountStatus;
import com.example.bankapi.account.exception.AccountNotFoundException;
import com.example.bankapi.account.repository.AccountRepository;
import com.example.bankapi.card.entity.Card;
import com.example.bankapi.card.enums.CardStatus;
import com.example.bankapi.card.enums.CardTierType;
import com.example.bankapi.card.exception.CardNotFoundException;
import com.example.bankapi.card.exception.DuplicateCardNumberException;
import com.example.bankapi.card.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    public CardService(CardRepository cardRepository, AccountRepository accountRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Card issue(String iban, CardTierType tier) {
        Account account = accountRepository.findByIban(iban)
                .orElseThrow(() -> new AccountNotFoundException(iban));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Cannot issue a card for a blocked account");
        }

        Card card = new Card(account, generateNumber(), generateCvv(),
                YearMonth.now().plusYears(4), tier);
        return cardRepository.save(card);
    }

    @Transactional(readOnly = true)
    public List<Card> getByAccountId(Long accountId) {
        return cardRepository.findByAccountId(accountId);
    }

    @Transactional
    public void block(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
        card.block();
    }

    private String generateNumber() {
        var random = ThreadLocalRandom.current();
        var sb = new StringBuilder("4");
        for (int i = 0; i < 15; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String generateCvv() {
        return String.format("%03d", ThreadLocalRandom.current().nextInt(1000));
    }
}