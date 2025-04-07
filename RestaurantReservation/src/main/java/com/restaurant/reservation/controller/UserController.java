package com.restaurant.reservation.controller;

import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.dto.UserResponseDTO;
import com.restaurant.reservation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // o define tu frontend específico
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO request) {
        UserResponseDTO response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    // Opción: Aquí puedes añadir login si usas autenticación personalizada o JWT
}
