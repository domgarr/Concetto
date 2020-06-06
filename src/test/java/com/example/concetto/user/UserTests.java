package com.example.concetto.user;

import com.example.concetto.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * User model will be used to find categories/concepts created by a user.
 * fields needed:
 * id:
 * We need a unique id to distinguish users. Google supports Oauth, which we are using to login.
 * There is a details object that includes an id and email. Th email is definitely unique. I am going to
 * generate an ID in the database and link it to the user's email. This means, when a user logins in. We will be
 * using their email to find their ID.
 * <p>
 * email:
 * Not much to be said here. Email will be used for finding a users concetto id. Could also
 * use the email for sending notifications.
 */
public class UserTests {
    @Test
    public void user_areRequiredFieldsAreImplemented_requiredFieldsAreImplementedAndContainsGetterSetters() {
        User user = new User();
        user.setId(1L);
        user.setEmail("placeholder@gmail.com");

        assertEquals(1L, user.getId());
        assertEquals("placeholder@gmail.com", user.getEmail());
    }
}
