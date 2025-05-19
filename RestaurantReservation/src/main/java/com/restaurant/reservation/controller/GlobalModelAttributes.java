package com.restaurant.reservation.controller;

import com.restaurant.reservation.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Controller advice to inject the currently authenticated user into the model.
 */
@ControllerAdvice
public class GlobalModelAttributes {

    /**
     * Adds the current user to the model for all views.
     * @param authentication current authentication object
     * @return CustomUserDetails or null
     */
    @ModelAttribute("currentUser")
    public CustomUserDetails populateCurrentUser(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        return null;
    }
}