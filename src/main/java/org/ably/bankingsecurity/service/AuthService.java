package org.ably.bankingsecurity.service;

import lombok.RequiredArgsConstructor;
import org.ably.bankingsecurity.domain.dto.LoginDTO;
import org.ably.bankingsecurity.domain.entities.User;
import org.ably.bankingsecurity.domain.enums.Role;
import org.ably.bankingsecurity.domain.request.LoginRequest;
import org.ably.bankingsecurity.domain.request.RegisterRequest;
import org.ably.bankingsecurity.mapper.UserMapper;
import org.ably.bankingsecurity.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;




    public User signup(RegisterRequest request) {
        User user = userMapper.toRegisterRequest(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        return userRepository.save(user);
    }

    public LoginDTO authenticate(LoginRequest request) {

        User user = (User) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        ).getPrincipal();


        String jwtToken = jwtService.generateToken(user);
        LoginDTO loginResponse = new LoginDTO(
                jwtToken,
                jwtService.getExpiration(),
                userMapper.toDTO(user) );


        return loginResponse;
    }


    }






