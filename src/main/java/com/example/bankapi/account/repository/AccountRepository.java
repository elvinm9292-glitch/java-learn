package com.example.bankapi.account.repository;

import com.example.bankapi.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a left join fetch a.cards where a.id = :id")
    Optional<Account> findByIdWithCards(Long id);

    @Query("select a from Account a left join fetch a.cards")
    List<Account> findAllWithCards();
    Optional<Account> findByIban(String iban);

}
