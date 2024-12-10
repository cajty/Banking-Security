package org.ably.bankingsecurity.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ably.bankingsecurity.domain.dto.LoginDTO;
import org.ably.bankingsecurity.domain.dto.UserDTO;
import org.ably.bankingsecurity.domain.entities.User;
import org.ably.bankingsecurity.domain.request.LoginRequest;
import org.ably.bankingsecurity.domain.request.RegisterRequest;
import org.ably.bankingsecurity.mapper.UserMapper;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.ws.rs.core.Response;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Collections;

@Service

public class KeycloakAuthService {

    private final Keycloak keycloak;
    private final UserService userService;
    private final UserMapper userMapper;

    public KeycloakAuthService(Keycloak keycloak, UserService userService, UserMapper userMapper) {
        this.keycloak = keycloak;
        this.userService = userService;
        this.userMapper = userMapper;
    }
private String realm = "bank-app";
private String clientId = "banking_security_application";
private String ClientSecret ="w2GYaChHMbRBEr2u1IMBf5SB2xf7RlXy" ;




  @Transactional
  public UserDTO register(RegisterRequest request) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.getEmail());
        userRepresentation.setEmail(request.getEmail());
        userRepresentation.setFirstName(request.getName());
        userRepresentation.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        credential.setTemporary(false);
        userRepresentation.setCredentials(Collections.singletonList(credential));

        userRepresentation.setRealmRoles(Collections.singletonList(request.getRole().name()));

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(userRepresentation);

        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new RuntimeException("Failed to create user in Keycloak: " + response.readEntity(String.class));
        }


        User user = userService.save(request);

        return userMapper.toDTO(user);
    }

public LoginDTO authenticate(LoginRequest request) {
    try {
        // Debug logs
        System.out.println("Authenticating user with email: " + request.getEmail());
        System.out.println("Using Keycloak realm: " + realm);
        System.out.println("Using Keycloak client ID: " + clientId);

        Keycloak tokenKeycloak = Keycloak.getInstance(
                "http://localhost:8080",
                realm,
                request.getEmail(),
                request.getPassword(),
                clientId,
                ClientSecret
        );

        System.out.println("Authentication successful");

        User user = userService.findByEmail(request.getEmail());
        UserDTO userDTO = userMapper.toDTO(user);
        System.out.println("User found: " + tokenKeycloak.tokenManager().getAccessToken().getToken());
        System.out.println("User found: " + tokenKeycloak.tokenManager().getAccessToken().getExpiresIn());

        return new LoginDTO(
                tokenKeycloak.tokenManager().getAccessToken().getToken(),
                tokenKeycloak.tokenManager().getAccessToken().getExpiresIn(),
                userDTO
        );
    } catch (Exception e) {
        e.printStackTrace(); // Log the full stack trace for debugging
        throw new RuntimeException("Authentication failed", e);
    }
}

}
