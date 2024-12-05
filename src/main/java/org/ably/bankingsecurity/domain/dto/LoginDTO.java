package org.ably.bankingsecurity.domain.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class LoginDTO {
    private String token;
    private long expiresIn;
    private UserDTO user;
}
