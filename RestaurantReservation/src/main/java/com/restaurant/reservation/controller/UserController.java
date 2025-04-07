package com.restaurant.reservation.controller;

import com.restaurant.reservation.dto.LoginRequestDTO;
import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.dto.UserResponseDTO;
import com.restaurant.reservation.service.AuthenticationService;
import com.restaurant.reservation.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody RegisterRequestDTO request) {
        UserResponseDTO response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        // Autenticar el usuario
        String token = authenticationService.authenticate(loginRequest);

        // Obtener el usuario desde el servicio
        UserResponseDTO user = userService.getUserByEmail(loginRequest.getEmail());

        // Crear la respuesta como un Map
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userType", user.getUserType().toString());


        return ResponseEntity.ok(response);
    }

}
