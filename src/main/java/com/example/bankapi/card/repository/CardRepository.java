package com.example.bankapi.card.repository;

import com.example.bankapi.account.entity.Account;
import com.example.bankapi.card.entity.Card;
import com.example.bankapi.card.enums.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByAccountId(Long accountId);

    List<Card> findByStatus(CardStatus status);

    @Query("select count(c) > 0 from Card c where c.account.id = :accountId and c.status = :status")
    boolean hasCardWithStatus(Long accountId, CardStatus status);
    boolean existsByNumber(String number);

}
