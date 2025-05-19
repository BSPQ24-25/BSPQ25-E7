package com.restaurant.reservation.controller;

import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.model.UserType;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that handles user registration.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Processes the user registration form submission.
     * @param username the new user's username
     * @param email the new user's email
     * @param phone the new user's phone
     * @param password the new user's password
     * @param userType the type of user (ADMIN/CUSTOMER)
     * @return redirect URL
     */
    @PostMapping("/register")
    public String registerForm(@RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String phone,
                                @RequestParam String password,
                                @RequestParam String userType) {

        if (userRepository.findByEmail(email).isPresent()) {
            return "redirect:/register.html?error=true";
        }

        RegisterRequestDTO dto = new RegisterRequestDTO(
                email,
                username,
                phone,
                password,
                UserType.valueOf(userType)
        );

        authService.register(dto);
        return "redirect:/login.html?registered=true";
    }
}
