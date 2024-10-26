package com.lucky.service;

import com.lucky.service.dto.UserDTO;
import com.lucky.service.dto.UserResponse;
import com.lucky.service.exceptions.UserNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private Validator validator;


    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("test@example.com", "John", "Doe", LocalDate.of(1990, 1, 1));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCreateUser_Success() {
        ResponseEntity<String> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User created successfully", response.getBody());
        verify(userService, times(1)).saveUser(any(UserDTO.class));
    }

    @Test
    void testUpdateUser_Success() {
        Long userId = 1L;
        ResponseEntity<String> response = userController.updateUser(userId, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", response.getBody());
        verify(userService, times(1)).updateUser(eq(userId), any(UserDTO.class));
    }

    @Test
    void testGetUsers_Success() {
        UserResponse userResponse = new UserResponse(1L, userDTO, userDTO.age());
        when(userService.getUsers()).thenReturn(List.of(userResponse));

        ResponseEntity<List<UserResponse>> response = userController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).getUsers();
    }

    @Test
    void testGetUserById_Success() {
        Long userId = 1L;
        UserResponse userResponse = new UserResponse(1L, userDTO, userDTO.age());
        when(userService.getUser(userId)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.getUser(userId);

        assertEquals(34, userResponse.age());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
        verify(userService, times(1)).getUser(userId);
    }

    @Test
    void testGetUserById_NotFound() {
        Long userId = 1L;
        when(userService.getUser(userId)).thenThrow(new UserNotFoundException("User not found"));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userController.getUser(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userService, times(1)).getUser(userId);
    }

    @Test
    void testDeleteUsers_Success() {
        ResponseEntity<String> response = userController.deleteUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Users has been removed successfully", response.getBody());
        verify(userService, times(1)).deleteUsers();
    }

    @Test
    void testDeleteUserById_Success() {
        Long userId = 1L;
        ResponseEntity<String> response = userController.deleterUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User has been removed successfully", response.getBody());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testCreateUser_InvalidEmailFormat() {
        UserDTO userDTO = new UserDTO("invalid_email", "John", "Doe", LocalDate.of(2000, 1, 1));

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Email must be valid"));
    }

    @Test
    void testCreateUser_AgeLessThan18() {
        UserDTO userDTO = new UserDTO("valid.email@example.com", "Jane", "Doe", LocalDate.now().minusYears(17)); // 17 years old

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("User must be at least 18 years old"));
    }
}
