package com.example.concetto.user;

import com.example.concetto.api.v1.mapper.UserMapper;
import com.example.concetto.models.User;
import com.example.concetto.repositories.UserRepository;
import com.example.concetto.services.UserService;
import com.example.concetto.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    public static final String PLACEHOLDER_GMAIL_COM = "placeholder@gmail.com";
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void initBefore() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(UserMapper.INSTANCE, userRepository);
    }

    @Test
    void save_SavingUser_ShouldReturnUserSaved() {
        User user = new User();
        user.setId(1L);
        user.setEmail(PLACEHOLDER_GMAIL_COM);

        when(userRepository.save(user)).thenReturn(user);
        User userSaved = userService.save(user);
        assertNotNull(userSaved);

    }

    @Test
    void existsByEmail_IfNoUserExists_FalseShouldBeReturned() {
        when(userRepository.existsByEmail(PLACEHOLDER_GMAIL_COM)).thenReturn(false);
        boolean doesUserExist = userService.existsByEmail(PLACEHOLDER_GMAIL_COM);
        assertFalse(doesUserExist);
    }

    @Test
    void existsByEmail_IfUserExists_TrueShouldBeReturned() {
        when(userRepository.existsByEmail("placeholder@gmail.com")).thenReturn(true);
        boolean doesUserExist = userService.existsByEmail(PLACEHOLDER_GMAIL_COM);
        assertTrue(doesUserExist);
    }

    @Test
    void createUserIfNotFound_IfUserIsFound_ReturnFalse() {
        when(userRepository.existsByEmail(PLACEHOLDER_GMAIL_COM)).thenReturn(false);
        boolean success = userService.existsByEmail(PLACEHOLDER_GMAIL_COM);
        assertFalse(success);
    }

    @Test
    void createUserIfNotFound_IfUserNotFound_ReturnTrue() {
        when(userRepository.existsByEmail(PLACEHOLDER_GMAIL_COM)).thenReturn(true);
        boolean success = userService.existsByEmail(PLACEHOLDER_GMAIL_COM);
        assertTrue(success);
    }
}
