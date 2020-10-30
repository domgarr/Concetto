package com.domgarr.concetto.user;

import com.domgarr.concetto.api.v1.mapper.UserMapper;
import com.domgarr.concetto.models.User;
import com.domgarr.concetto.repositories.UserRepository;
import com.domgarr.concetto.services.UserService;
import com.domgarr.concetto.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private static final String PLACEHOLDER_GMAIL_COM = "placeholder@gmail.com";
    private static BigInteger t = new BigInteger("1");
    private static BigInteger f = new BigInteger("0");


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
        when(userRepository.existsByEmail(PLACEHOLDER_GMAIL_COM)).thenReturn(f);
        boolean doesUserExist = userService.existsByEmail(PLACEHOLDER_GMAIL_COM);
        assertFalse(doesUserExist);
    }

    @Test
    void existsByEmail_IfUserExists_TrueShouldBeReturned() {
        when(userRepository.existsByEmail("placeholder@gmail.com")).thenReturn(t);
        boolean doesUserExist = userService.existsByEmail(PLACEHOLDER_GMAIL_COM);
        assertTrue(doesUserExist);
    }

    @Test
    void createUserIfNotFound_IfUserIsFound_ReturnFalse() {
        when(userRepository.existsByEmail(PLACEHOLDER_GMAIL_COM)).thenReturn(f);
        boolean success = userService.existsByEmail(PLACEHOLDER_GMAIL_COM);
        assertFalse(success);
    }

    @Test
    void createUserIfNotFound_IfUserNotFound_ReturnTrue() {
        when(userRepository.existsByEmail(PLACEHOLDER_GMAIL_COM)).thenReturn(t);
        boolean success = userService.existsByEmail(PLACEHOLDER_GMAIL_COM);
        assertTrue(success);
    }
}
