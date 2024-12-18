package org.ably.bankingsecurity.service;
import io.jsonwebtoken.Claims;
import org.ably.bankingsecurity.domain.entities.User;
import org.ably.bankingsecurity.domain.enums.Role;
import org.ably.bankingsecurity.service.JwtService;
import org.junit.jupiter.api.*;
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

    private final String SECRET_KEY = "Your32CharacterLongBase64EncodedSecretKeydvererberbbe";
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
        testUser.setEmail("test@example.com"); // Important to set username since it's used in token generation

        generatedToken = jwtService.generateToken(testUser);
    }

    @Nested
    @DisplayName("Token Generation Tests")
    class TokenGenerationTests {

        @Test
        @DisplayName("Should generate valid token with correct claims")
        void shouldGenerateValidTokenWithCorrectClaims() {
            String token = jwtService.generateToken(testUser);
            Claims claims = jwtService.extractAllClaims(token);

            assertNotNull(token);
            assertEquals(testUser.getEmail(), claims.getSubject());
            assertEquals(testUser.getRole().toString(), claims.get("role").toString());
            assertEquals(testUser.getId().intValue(), ((Integer) claims.get("userId")).intValue());
        }

        @Test
        @DisplayName("Should generate token with correct expiration time")
        void shouldGenerateTokenWithCorrectExpiration() {
            String token = jwtService.generateToken(testUser);
            Date expirationDate = jwtService.extractClaim(token, Claims::getExpiration);

            long expectedExpirationTime = System.currentTimeMillis() + EXPIRATION_TIME;
            long actualExpirationTime = expirationDate.getTime();

            // Allow 5 seconds tolerance for test execution time
            assertTrue(Math.abs(expectedExpirationTime - actualExpirationTime) < 5000);
        }
    }

    @Nested
    @DisplayName("Token Validation Tests")
    class TokenValidationTests {

        @Test
        @DisplayName("Should validate token for correct user")
        void shouldValidateTokenForCorrectUser() {
            UserDetails userDetails = mock(UserDetails.class);
            when(userDetails.getUsername()).thenReturn(testUser.getEmail());

            boolean isValid = jwtService.isTokenValid(generatedToken, userDetails);

            assertTrue(isValid);
        }

        @Test
        @DisplayName("Should invalidate token for incorrect user")
        void shouldInvalidateTokenForIncorrectUser() {
            UserDetails userDetails = mock(UserDetails.class);
            when(userDetails.getUsername()).thenReturn("wrong@example.com");

            boolean isValid = jwtService.isTokenValid(generatedToken, userDetails);

            assertFalse(isValid);
        }
    }

    @Nested
    @DisplayName("Token Extraction Tests")
    class TokenExtractionTests {

        @Test
        @DisplayName("Should extract username correctly")
        void shouldExtractUsernameCorrectly() {
            String username = jwtService.extractUsername(generatedToken);
            assertEquals(testUser.getEmail(), username);
        }

        @Test
        @DisplayName("Should extract all claims correctly")
        void shouldExtractAllClaimsCorrectly() {
            Claims claims = jwtService.extractAllClaims(generatedToken);

            assertNotNull(claims);
            assertEquals(testUser.getEmail(), claims.getSubject());
            assertEquals(testUser.getRole().toString(), claims.get("role").toString());
            assertEquals(testUser.getId().intValue(), ((Integer) claims.get("userId")).intValue());
        }

        @Test
        @DisplayName("Should return correct expiration time")
        void shouldReturnCorrectExpirationTime() {
            long expiration = jwtService.getExpiration();
            assertEquals(EXPIRATION_TIME, expiration);
        }
    }
}