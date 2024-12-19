package org.ably.bankingsecurity.domain.request;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.bankingsecurity.domain.enums.Role;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Monthly income is required")
    @PositiveOrZero(message = "Monthly income cannot be negative")
    private Double monthlyIncome;

    @Min(value = 300, message = "Credit score must be at least 300")
    @Max(value = 850, message = "Credit score cannot exceed 850")
    private int creditScore;

    @NotBlank
    private Role role;


}




