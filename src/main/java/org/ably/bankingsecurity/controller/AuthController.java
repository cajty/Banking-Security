package org.ably.bankingsecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ably.bankingsecurity.domain.dto.LoginDTO;
import org.ably.bankingsecurity.domain.entities.User;
import org.ably.bankingsecurity.domain.request.LoginRequest;
import org.ably.bankingsecurity.domain.request.RegisterRequest;
import org.ably.bankingsecurity.mapper.UserMapper;
import org.ably.bankingsecurity.service.AuthService;
import org.ably.bankingsecurity.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Authentication APIs")
public class AuthController {

    private final AuthService authService;

    private final UserMapper userMapper;



    @Operation
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerUserDto) {
        User registeredUser = authService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }
    @Operation(summary = "Authenticate user")
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> authenticate(@RequestBody LoginRequest loginUserDto) {
        try {
            return ResponseEntity.ok(authService.authenticate(loginUserDto));
        } catch (Exception e) {

            e.printStackTrace();


            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginDTO("Token generation failed", 0, null));
        }

     




    }




}
