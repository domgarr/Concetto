package com.domgarr.concetto.utility;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * Contains helper methods for extracting fields from OAuth2Authentication.
 * <p>
 * For example, the details object contains many useful fields for personalizing a web app. It also contains an email,
 * which can be used to uniquely identify users.
 */

@Component
public class AuthUtility {
    public static final String EMAIL = "email";

    private AuthUtility() {
    }

    public String getEmail(OAuth2User principal) {
        return principal.getAttribute(EMAIL);
    }
}
