package com.example.concetto.utility;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.LinkedHashMap;

/**
 * Contains helper methods for extracting fields from OAuth2Authentication.
 * <p>
 * For example, the details object contains many useful fields for personalizing a web app. It also contains an email,
 * which can be used to uniquely identify users.
 */

public class AuthUtility {
    public static final String EMAIL = "email";

    private AuthUtility() {
    }

    public static String getEmail(OAuth2Authentication authentication) {
        LinkedHashMap<String, Object> detailProperties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
        return detailProperties.get(EMAIL).toString();
    }
}
