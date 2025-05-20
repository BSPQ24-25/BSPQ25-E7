package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling user redirection after login based on their role.
 */
@Controller
public class HomeController {

    private final UserRepository userRepository;

    /**
     * Constructs the controller with the user repository.
     * @param userRepository Repository for user-related database operations.
     */
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Redirects the logged-in user to the appropriate dashboard based on user type.
     * @return Redirect URL string.
     */
    @GetMapping("/home")
    public String homePage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (user.getUserType().name().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/customer/dashboard";
        }
    }
}
