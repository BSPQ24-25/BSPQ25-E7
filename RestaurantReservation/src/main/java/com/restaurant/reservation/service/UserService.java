package com.restaurant.reservation.service;

import com.restaurant.reservation.model.Customer;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.dto.UserRegistrationDTO;
import com.restaurant.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User registerUser(UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByUsername(registrationDTO.getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, 
                "El email " + registrationDTO.getEmail() + " ya está registrado"
            );
        }
        
        User user = new Customer(); 
        user.setUsername(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setName(registrationDTO.getName());
        user.setPhone(registrationDTO.getPhone());
        
        return userRepository.save(user);
    }

    // Eliminar @Autowired del método y solo declararlo
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
