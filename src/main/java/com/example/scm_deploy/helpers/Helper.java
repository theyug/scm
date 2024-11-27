package com.example.scm_deploy.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    private static Logger logger = LoggerFactory.getLogger(Helper.class);

    public static String getEmailOfloggedinUser(Authentication authentication) {
        String username = "";

        // Check if the authentication is an instance of OAuth2AuthenticationToken
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String clientId = oauthToken.getAuthorizedClientRegistrationId();

            if (clientId.equalsIgnoreCase("google")) {
                username = oauth2User.getAttribute("email");
                logger.info("Email is from Google: " + username);

            } else if (clientId.equalsIgnoreCase("github")) {
                // GitHub does not always return email, so we fall back to 'login' if email is not available
                System.out.println("Getting email from github");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                        : oauth2User.getAttribute("login").toString() + "@gmail.com";
            }

        } else {
            // Handle standard authentication (e.g., form login) where the principal is a UserDetails instance
            logger.info("Email is from local database");
            username = authentication.getName(); // returns the username (or email) from form login
        }

        return username;
    }
}
