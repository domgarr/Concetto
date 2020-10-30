package com.domgarr.concetto.controllers.v1;

import com.domgarr.concetto.models.Subject;
import com.domgarr.concetto.services.SubjectService;
import com.domgarr.concetto.services.UserService;
import com.domgarr.concetto.utility.AuthUtility;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {
    private final UserService userService;
    private final SubjectService subjectService;
    private final AuthUtility authUtility;

    public UserController(UserService userService, SubjectService subjectService, AuthUtility authUtility) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.authUtility = authUtility;
    }

    //https://spring.io/guides/tutorials/spring-boot-oauth2/
    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        String successMessage = "Successful authentication.";

        String email = authUtility.getEmail(principal);
        if (userService.createUserIfNotFound(email)) {
            successMessage = "Account created. ".concat(successMessage);
            /* When a user is first created. Add a default subject to the new user's account;
             A default subject is needed because Concepts should be categorized and when a user first creates an account
             they might not of created a subject yet. To prevent any errors a default subject is added.
             */
            Subject subject = new Subject();
            subject.setName("Default");
            subject.setUser(userService.getUserByEmail(authUtility.getEmail(principal)));
            subjectService.save(subject);
        }
        //Return a json containing a field
        HashMap<String, String> jsonMap = new HashMap<>();
        jsonMap.put("message", successMessage);
        //For the time being, returning a success message will suffice. If in the future, data is needed for some operation we can add it here.
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
}