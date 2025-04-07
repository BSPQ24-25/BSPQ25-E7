package com.restaurant.reservation.controller;

import com.restaurant.reservation.dto.LoginRequestDTO;
import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.dto.UserResponseDTO;
import com.restaurant.reservation.service.AuthenticationService;
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
    private final AuthenticationService authenticationService;


    

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO request) {
        UserResponseDTO response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        authenticationService.authenticate(loginRequest);
        // Si llegamos aquí, autenticación exitosa
        return ResponseEntity.ok("Login exitoso");
    }


}
