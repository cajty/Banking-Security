package org.ably.bankingsecurity.domain.request;




import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ably.bankingsecurity.domain.enums.Role;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 30)
    private String password;

    @NotBlank(message = "name is required")
    @Size(min = 8, max = 30)
    private String name;


    @NotBlank(message = "Email is required")
    private String email;

    @Max(value = 80)
    @Min(value = 18)
    @NotNull
    private int age;

    @Min(value = 0)
    private Double monthlyIncome;

    @Min(value = 0)
    private int creditScore;

    @NotBlank
    @Size(min = 8, max = 30)
    private String username;

    private Role role = Role.USER;

}