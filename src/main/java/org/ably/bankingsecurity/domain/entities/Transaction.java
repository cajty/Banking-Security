package org.ably.bankingsecurity.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.bankingsecurity.domain.enums.TransactionType;

@Data
@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private TransactionType type;


    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private Account accountOfSender;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account accountOfReceiver;
}
