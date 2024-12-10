package org.ably.bankingsecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ably.bankingsecurity.domain.dto.LoginDTO;
import org.ably.bankingsecurity.domain.dto.UserDTO;
import org.ably.bankingsecurity.domain.entities.User;
import org.ably.bankingsecurity.domain.request.LoginRequest;
import org.ably.bankingsecurity.domain.request.RegisterRequest;
import org.ably.bankingsecurity.service.KeycloakAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authKeyCloak")
@RequiredArgsConstructor
@Tag(name = "Auth of key cloak Controller", description = "Authentication APIs")
public class AuthKeyCloakController {

    private final KeycloakAuthService keycloakAuthService;

    @Operation(summary = "Register new user")
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest registerRequest) {
        try {
            return ResponseEntity.ok(keycloakAuthService.register(registerRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Authenticate user")
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> authenticate(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(keycloakAuthService.authenticate(loginRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginDTO("Authentication failed", 0, null));
        }
    }
}