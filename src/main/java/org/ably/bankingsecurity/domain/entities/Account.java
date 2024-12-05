package org.ably.bankingsecurity.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.bankingsecurity.domain.enums.AccountStatus;

import java.util.UUID;

@Data
@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private AccountStatus status;
}