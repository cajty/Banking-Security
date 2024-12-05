package org.ably.bankingsecurity.service;

import lombok.RequiredArgsConstructor;
import org.ably.bankingsecurity.domain.entities.User;
import org.ably.bankingsecurity.domain.enums.Role;
import org.ably.bankingsecurity.domain.request.LoginRequest;
import org.ably.bankingsecurity.domain.request.RegisterRequest;
import org.ably.bankingsecurity.mapper.UserMapper;
import org.ably.bankingsecurity.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;




    public User signup(RegisterRequest request) {
        User user = userMapper.toRegisterRequest(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        return userRepository.save(user);
    }

    public User authenticate(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));

        return user;
    }






}