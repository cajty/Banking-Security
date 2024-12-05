package org.ably.bankingsecurity.domain.request;


import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequest {

    @Positive(message = "Amount must be a positive value greater than 1000")
    @Min(value = 1000, message = "Amount must be at least 1000")
    private Double amount;

    @Positive(message = "Amount must be a positive value greater than 100")
    @Min(value = 100, message = "Amount must be at least 100")
    private Double mouthlyPayment;

}
