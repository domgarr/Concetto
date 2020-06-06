package com.example.concetto.user;

import com.example.concetto.controllers.v1.UserController;
import com.example.concetto.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


public class UserControllerTest {
    public static final String PLACEHOLDER_GMAIL_COM = "placeholder@gmail.com";

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void user_IfUserExists_StatusOKAndSuccessMessageContainsIndicatesNoCreationOfUserIsReturned() throws Exception {
        //TODO: Figure out how to test the UserController.
        /*
        when(userService.createUserIfNotFound("placeholder@gmail.com")).thenReturn(false);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Successful authentication."));
         */
    }


}
