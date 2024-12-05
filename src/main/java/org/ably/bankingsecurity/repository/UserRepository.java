package org.ably.bankingsecurity.repository;

import org.ably.bankingsecurity.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}
