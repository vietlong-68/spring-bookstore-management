package com.codegym.bookstore_management.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.codegym.bookstore_management.model.User;
import com.codegym.bookstore_management.service.SomethingConverter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomSuccessHandleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final SomethingConverter somethingConverter;

    public CustomSuccessHandleAuthenticationSuccessHandler(SomethingConverter somethingConverter) {
        this.somethingConverter = somethingConverter;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = somethingConverter.getCurrentUserFromUserDetails(userDetails);

        HttpSession session = request.getSession();
        session.setAttribute("currentUser", user);
    }

}
