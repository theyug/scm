package com.example.scm_deploy.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.util.List;
import com.example.scm_deploy.entities.Providers;
import  com.example.scm_deploy.entities.User;
import com.example.scm_deploy.helpers.AppConstants;
import com.example.scm_deploy.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserRepo repo;
    Logger logger = LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;
    private Object user;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenicationSuccessHandler");
        //identify the provider
        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);
        var oauthuser = (DefaultOAuth2User) authentication.getPrincipal();
        oauthuser.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEnabled(true);
        user.setEmailVerified(true);

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            user.setEmail(oauthuser.getAttribute("email").toString());
            user.setProfilePic(oauthuser.getAttribute("picture"));
            user.setName(oauthuser.getAttribute("name").toString());
            user.setProviderUserId(oauthuser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using google");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            String email = oauthuser.getAttribute("email") != null ? oauthuser.getAttribute("email").toString()
                    : oauthuser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthuser.getAttribute("avatar_url").toString();
            String name = oauthuser.getAttribute("login").toString();
            String providerUserId = oauthuser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);

            user.setAbout("This account is created using github");



        } else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedIn")) {


        } else {
            logger.info("Unknown provider");
        }
        //database saver
//        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
////       logger.info(user.getName());
////       user.getAttributes().forEach((key , value)->{
////       logger.info("{}=>{}",key , value);
////        });
////       logger.info(user.getAuthorities().toString());
//        String email = user.getAttribute("email").toString();
//        String name = user.getAttribute("name").toString();
//        String picture = user.getAttribute("picture").toString();
//        //create a usr
//        User user1 = new User();
//        user1.setEmail(email);
//        user1.setName(name);
//        user1.setProfilePic(picture);
//        user1.setPassword("password");
//        user1.setUserId(UUID.randomUUID().toString());
//        user1.setProvider(Providers.GOOGLE);
//        user1.setProviderUserId(user.getName());
//        user1.setEnabled(true);
//        user1.setEmailVerified(true);
//        user1.setRoleList(List.of(AppConstants.ROLE_USER));
//        user1.setAbout("acc set through google");
//  User user2 =userRepo.findByemail(email).orElse(null);
//  if(user2==null){
//      userRepo.save(user1);
//      logger.info("User ssaved");
//  }
        User user2 = userRepo.findByemail(user.getEmail()).orElse(null);
        if (user2 == null) {
            userRepo.save(user);
            System.out.println("user saved:" + user.getEmail());
        }


        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
