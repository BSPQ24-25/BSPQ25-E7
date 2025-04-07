package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String homePage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (user.getUserType().name().equals("ADMIN")) {
            return "redirect:/admin/dashboard";  // Redirige a la página de admin
        } else {
            return "redirect:/customer/dashboard";  // Redirige a la página del cliente
        }
    }
}
