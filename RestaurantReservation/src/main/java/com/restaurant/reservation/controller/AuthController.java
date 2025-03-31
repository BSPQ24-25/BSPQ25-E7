package com.restaurant.reservation.controller;

import com.restaurant.reservation.dto.UserRegistrationDTO;
import com.restaurant.reservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Endpoint para registro de usuario
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        userService.registerUser(registrationDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    // Endpoint para login (aquí puedes agregar autenticación real más adelante)
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserRegistrationDTO loginDTO) {
        // Implementar lógica de autenticación (ej. JWT o session)
        return ResponseEntity.ok("Login successful");
    }
}
