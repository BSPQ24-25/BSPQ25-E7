package com.restaurant.reservation.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String role = userDetails.getUser().getUserType().name();

        if (role.equals("ADMIN")) {
            response.sendRedirect("/admin.html");
        } else if (role.equals("CUSTOMER")) {
            response.sendRedirect("/customer.html");
        } else {
            response.sendRedirect("/index.html"); // Por si acaso
        }
    }
}
