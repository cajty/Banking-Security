package org.ably.bankingsecurity.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.bankingsecurity.domain.enums.TransactionType;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private Double amount;
    private TransactionType type;
    private UUID accountOfSender;
    private UUID accountOfReceiver;
}
