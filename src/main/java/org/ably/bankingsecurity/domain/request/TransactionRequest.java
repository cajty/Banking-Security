package org.ably.bankingsecurity.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be more than 0")
    private Double amount;



    @NotNull(message = "Account number of that sender   cannot be null")
    private UUID accountSenderId;

    @NotNull(message = "Account number of that receiver  cannot be null")
    private UUID accountReceiverId;
}
