package com.restaurant.reservation.controller;

import com.restaurant.reservation.dto.LoginRequestDTO;
import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.model.UserType;
import com.restaurant.reservation.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String loginForm(@RequestParam String email,
                            @RequestParam String password) {
        return authService.login(new LoginRequestDTO(email, password));
    }

    @PostMapping("/register")
    public String registerForm(@RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String phone,
                                @RequestParam String password,
                                @RequestParam String userType) {

        RegisterRequestDTO dto = new RegisterRequestDTO(
                email,
                username,
                phone,
                password,
                UserType.valueOf(userType)
        );

        return authService.register(dto);
    }
}
