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

/**
 * REST controller that handles user authentication operations such as registration and login.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    /**
     * Constructs the controller with required services.
     * @param userService Service for user-related operations.
     * @param authenticationService Service for authentication logic.
     */
    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    /**
     * Registers a new user in the system.
     * @param request The registration request containing user details.
     * @return The response containing registered user information.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody RegisterRequestDTO request) {
        UserResponseDTO response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Authenticates a user and returns a JWT token along with user type.
     * @param loginRequest Login request containing email and password.
     * @return A map containing the authentication token and user type.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        String token = authenticationService.authenticate(loginRequest);
        UserResponseDTO user = userService.getUserByEmail(loginRequest.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userType", user.getUserType().toString());

        return ResponseEntity.ok(response);
    }

}

