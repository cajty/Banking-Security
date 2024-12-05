package org.ably.bankingsecurity.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.bankingsecurity.domain.enums.Role;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private int age;
    private Double monthlyIncome;
    private int creditScore;
    private Role role;
    private List<UUID> accountIds;
    private List<Long> invoiceIds;
    private List<Long> loanIds;
}
