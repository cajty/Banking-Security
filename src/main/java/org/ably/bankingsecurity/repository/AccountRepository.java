package org.ably.bankingsecurity.repository;

import org.ably.bankingsecurity.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
     List<Account> findByUserId(Long userId);
}
