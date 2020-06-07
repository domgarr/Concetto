package com.example.concetto.controllers.v1;

import com.example.concetto.services.UserService;
import com.example.concetto.utility.AuthUtility;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //https://spring.io/guides/tutorials/spring-boot-oauth2/
    @RequestMapping("/user")
    public HashMap<String, String> user(OAuth2Authentication authentication) {
        String successMessage = "Successful authentication.";

        String email = AuthUtility.getEmail(authentication);
        if (userService.createUserIfNotFound(email)) {
            successMessage = "Account created. ".concat(successMessage);
        }

        //Return a json containing a field
        HashMap<String, String> jsonMap = new HashMap<>();
        jsonMap.put("message", successMessage);
        //For the time being, returning a success message will suffice. If in the future, data is needed for some operation we can add it here.
        return jsonMap;
    }
}