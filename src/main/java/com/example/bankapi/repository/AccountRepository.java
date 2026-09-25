package com.example.bankapi.repository;

import com.example.bankapi.entity.Account;
import org.hibernate.internal.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a left join fetch a.cards where a.id = :id")
    Optional<Account> findByIdWithCards(Long id);
}
