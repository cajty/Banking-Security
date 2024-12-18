package org.ably.bankingsecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.ably.bankingsecurity.domain.entities.User;
import org.ably.bankingsecurity.domain.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private final long EXPIRATION_TIME = 864000000;
    private User testUser;
    private String generatedToken;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET_KEY);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", EXPIRATION_TIME);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setRole(Role.USER);

        generatedToken = jwtService.generateToken(testUser);
    }

    @Nested
    @DisplayName("Token Generation Tests")
    class TokenGenerationTests {

        @Test
        @DisplayName("Should generate valid token with correct claims")
        void shouldGenerateValidTokenWithCorrectClaims() {
            // When
            String token = jwtService.generateToken(testUser);
            Claims claims = jwtService.extractAllClaims(token);

            // Then
            assertNotNull(token);
            assertEquals(testUser.getEmail(), claims.getSubject());
            assertEquals(testUser.getRole().name(), claims.get("role"));
            assertEquals(testUser.getId(), Long.valueOf((Integer) claims.get("userId")));
        }

        @Test
        @DisplayName("Should generate token with correct expiration time")
        void shouldGenerateTokenWithCorrectExpiration() {
            // When
            String token = jwtService.generateToken(testUser);
            Date expirationDate = jwtService.extractClaim(token, Claims::getExpiration);

            // Then
            long expectedExpirationTime = System.currentTimeMillis() + EXPIRATION_TIME;
            long actualExpirationTime = expirationDate.getTime();

            // Allow 1 second tolerance for test execution time
            assertTrue(Math.abs(expectedExpirationTime - actualExpirationTime) < 1000);
        }
    }

    @Nested
    @DisplayName("Token Validation Tests")
    class TokenValidationTests {

        @Test
        @DisplayName("Should validate token for correct user")
        void shouldValidateTokenForCorrectUser() {
            // Given
            UserDetails userDetails = mock(UserDetails.class);
            when(userDetails.getUsername()).thenReturn(testUser.getEmail());

            // When
            boolean isValid = jwtService.isTokenValid(generatedToken, userDetails);

            // Then
            assertTrue(isValid);
        }

        @Test
        @DisplayName("Should invalidate token for incorrect user")
        void shouldInvalidateTokenForIncorrectUser() {
            // Given
            UserDetails userDetails = mock(UserDetails.class);
            when(userDetails.getUsername()).thenReturn("wrong@example.com");

            // When
            boolean isValid = jwtService.isTokenValid(generatedToken, userDetails);

            // Then
            assertFalse(isValid);
        }

//        @Test
//        @DisplayName("Should invalidate expired token")
//        void shouldInvalidateExpiredToken() {
//            // Given
//            ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1); // Set to 1ms
//            String expiredToken = jwtService.generateToken(testUser);
//            UserDetails userDetails = mock(UserDetails.class);
//            when(userDetails.getUsername()).thenReturn(testUser.getEmail());
//
//            // Wait for token to expire
//            try {
//                Thread.sleep(2);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//
//            // When & Then
//            assertFalse(jwtService.isTokenValid(expiredToken, userDetails));
//        }
   }

    @Nested
    @DisplayName("Token Extraction Tests")
    class TokenExtractionTests {

        @Test
        @DisplayName("Should extract username correctly")
        void shouldExtractUsernameCorrectly() {
            // When
            String username = jwtService.extractUsername(generatedToken);

            // Then
            assertEquals(testUser.getEmail(), username);
        }

        @Test
        @DisplayName("Should extract all claims correctly")
        void shouldExtractAllClaimsCorrectly() {
            // When
            Claims claims = jwtService.extractAllClaims(generatedToken);

            // Then
            assertNotNull(claims);
            assertEquals(testUser.getEmail(), claims.getSubject());
            assertEquals(testUser.getRole().name(), claims.get("role"));
            assertEquals(testUser.getId(), Long.valueOf((Integer) claims.get("userId")));
        }

        @Test
        @DisplayName("Should throw ExpiredJwtException for expired token")
        void shouldThrowExceptionForExpiredToken() {
            // Given
            ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1); // Set to 1ms
            String expiredToken = jwtService.generateToken(testUser);

            // Wait for token to expire
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // When & Then
            assertThrows(ExpiredJwtException.class, () -> jwtService.extractAllClaims(expiredToken));
        }
    }

    @Test
    @DisplayName("Should return correct expiration time")
    void shouldReturnCorrectExpirationTime() {
        // When
        long expiration = jwtService.getExpiration();

        // Then
        assertEquals(EXPIRATION_TIME, expiration);
    }
}