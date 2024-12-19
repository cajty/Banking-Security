
package org.ably.bankingsecurity.service;

import org.ably.bankingsecurity.domain.entities.User;
import org.ably.bankingsecurity.domain.request.UserRequest;
import org.ably.bankingsecurity.mapper.UserMapper;
import org.ably.bankingsecurity.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserRequest testUserRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        testUserRequest = new UserRequest();
        testUserRequest.setEmail("test@example.com");
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.findAll();

        // Assert
        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        User foundUser = userService.findById(1L);

        // Assert
        assertNotNull(foundUser);
        assertEquals(testUser.getId(), foundUser.getId());
        assertEquals(testUser.getEmail(), foundUser.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenUserDoesNotExist_ShouldThrowException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.findById(1L));
        assertEquals("User not found with id 1", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void save_ShouldSaveAndReturnUser() {
        // Arrange
        when(userMapper.toEntity(testUserRequest)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        User savedUser = userService.save(testUserRequest);

        // Assert
        assertNotNull(savedUser);
        assertEquals(testUser.getEmail(), savedUser.getEmail());
        verify(userMapper, times(1)).toEntity(testUserRequest);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void delete_ShouldDeleteUser() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);

        // Act
        userService.delete(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByEmail_WhenUserExists_ShouldReturnUser() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));

        // Act
        User foundUser = userService.findByEmail(email);

        // Assert
        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void findByEmail_WhenUserDoesNotExist_ShouldThrowException() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.findByEmail(email));
        assertEquals("User not found with email: " + email, exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
    }
}