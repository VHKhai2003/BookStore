package com.example.bookstore.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
            System.out.println("Authentication exception: " + exception);
            
            // if(exception instanceof InternalAuthenticationServiceException) {
            //     response.sendRedirect("/auth/login?error=blocked");
            //     return;
            // }

            if(exception.getMessage().equalsIgnoreCase("User was blocked")) {
                response.sendRedirect("/auth/login?error=blocked");
                return;
            }
            response.sendRedirect("/auth/login?error");
    }

}
