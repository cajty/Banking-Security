package org.ably.bankingsecurity.repository;

import org.ably.bankingsecurity.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
    SELECT  t
    FROM Transaction t 
    JOIN t.accountOfSender a1 
    JOIN t.accountOfReceiver a2 
    WHERE a1.user.id = :userId OR a2.user.id = :userId
    """)
    List<Transaction> findByUserId(Long userId);



}
