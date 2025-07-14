package com.progcreek.app.api;

import com.progcreek.app.exception.UserNotFoundException;
import com.progcreek.app.model.User;
import com.progcreek.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUser_Success() {
        // Arrange
        String userId = "1";
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setName("John Doe");
        mockUser.setEmail("john.doe@example.com");
        when(userService.getUserById(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<User> response = userController.getUser(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(userId, response.getBody().getId());
    }

    @Test
    void testGetUser_NotFound() {
        // Arrange
        String userId = "1";
        when(userService.getUserById(userId)).thenReturn(null);

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userController.getUser(userId);
        });
        assertEquals("User with ID 1 not found", exception.getMessage());
    }

    @Test
    void testCreateUser_Success() {
        // Arrange
        User newUser = new User();
        newUser.setId("2");
        newUser.setName("Jane Doe");
        newUser.setEmail("jane.doe@example.com");
        when(userService.createUser(newUser)).thenReturn(newUser);

        // Act
        ResponseEntity<User> response = userController.createUser(newUser);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Jane Doe", response.getBody().getName());
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        String userId = "1";
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("John Updated");
        updatedUser.setEmail("john.updated@example.com");
        when(userService.updateUser(userId, updatedUser)).thenReturn(updatedUser);

        // Act
        ResponseEntity<User> response = userController.updateUser(userId, updatedUser);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("John Updated", response.getBody().getName());
    }
}