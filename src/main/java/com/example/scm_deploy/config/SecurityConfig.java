package com.example.scm_deploy.config;

import com.example.scm_deploy.services.impl.SecurityCustomerUserdetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.net.http.HttpClient;

@Configuration
public class SecurityConfig {
    @Autowired
    private OAuthAuthenicationSuccessHandler handler;


    @Bean
    public SecurityCustomerUserdetailService securityCustomerUserdetailService() {
        return new SecurityCustomerUserdetailService();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(SecurityCustomerUserdetailService userdetailService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userdetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Configuring HttpSecurity for form login and authorization rules
        httpSecurity
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/user/**").authenticated() // Require authentication for /user/**
                            .anyRequest().permitAll();  // Allow all other requests without authentication
                })
                .formLogin(formLogin -> {
                    formLogin
                            .loginPage("/login") // Custom login page
                            .loginProcessingUrl("/authenticate") // URL to submit the login form
                            .defaultSuccessUrl("/user/profile", true) // Redirect after successful login
//                            .failureUrl("/login?error=true") // Redirect after failed login
                            .usernameParameter("email") // Custom parameter name for email
                            .passwordParameter("password"); // Custom parameter name for password
                });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm->{
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });
        //oauth configs
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(handler);

        });

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
