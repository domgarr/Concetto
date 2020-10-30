package com.domgarr.concetto.user;

import com.domgarr.concetto.models.User;
import com.domgarr.concetto.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserRepositoryTest {
    public static final String ASD_GMAIL_COM = "asd@gmail.com";
    public static final String INVALID_GMAIL_COM = "invalid@gmail.com";
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void init() {
        user = new User();
        user.setEmail(ASD_GMAIL_COM);
    }

    @Test
    void existsByEmail_GivenValidEmailStoredInDb_ReturnTrue() {
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);

        BigInteger userExists = userRepository.existsByEmail(ASD_GMAIL_COM);
        assertTrue(userExists.intValue() != 0);
    }

    @Test
    void existsByEmail_GivenInvalidEmailStoredInDb_ReturnFalse() {
        BigInteger userExists = userRepository.existsByEmail(INVALID_GMAIL_COM);
        assertFalse(userExists.intValue() != 0);
    }


}


