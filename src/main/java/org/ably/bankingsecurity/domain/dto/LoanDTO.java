package org.ably.bankingsecurity.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    private Long id;
    private Double amount;
    private Double interestRate;
    private int termMonths;
    private Double monthlyPayment;
    private boolean approved;
    private Long userId;
    private Long approvedByUserId;
}
