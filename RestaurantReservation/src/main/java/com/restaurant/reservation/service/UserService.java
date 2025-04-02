package com.restaurant.reservation.service;

import com.restaurant.reservation.dto.UserRegistrationDTO;
import com.restaurant.reservation.model.Customer;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.model.Admin;
import com.restaurant.reservation.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Constructor con dependencias
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByUsername(registrationDTO.getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "The email " + registrationDTO.getEmail() + " is already registered"
            );
        }

        // Aquí estamos determinando si el usuario es un Customer o Admin. 
        // Usamos registrationDTO.getType() para determinar qué clase instanciar.
        User user;
        
        // Suponiendo que registrationDTO tiene un campo `type` que define si es un "customer" o "admin"
        if ("customer".equalsIgnoreCase(registrationDTO.getType())) {
            user = new Customer();
        } else if ("admin".equalsIgnoreCase(registrationDTO.getType())) {
            user = new Admin();
        } else {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid user type"
            );
        }

        user.setGmail(registrationDTO.getEmail());
        user.setName(registrationDTO.getName());
        user.setPhone(registrationDTO.getPhone());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        return userRepository.save(user);
    }

    // Método para encontrar un usuario por su nombre de usuario (email)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username); // Método que busca por email
    }
}
