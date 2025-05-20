package com.restaurant.reservation.service;

import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.dto.UserResponseDTO;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the UserService interface.
 * Manages user registration and retrieval.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs the service with required dependencies.
     *
     * @param userRepository Repository for user persistence.
     * @param passwordEncoder Encoder to hash user passwords.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user after validating that the email does not already exist.
     * The password is encrypted before storing the user.
     *
     * @param dto DTO containing user registration information.
     * @return A UserResponseDTO with the newly created user's information.
     * @throws RuntimeException if the email is already registered.
     */
    @Override
    public UserResponseDTO registerUser(RegisterRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        User user = new User(
            dto.getEmail(),
            dto.getUsername(),
            dto.getPhone(),
            passwordEncoder.encode(dto.getPassword()),
            dto.getUserType()
        );

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(
            savedUser.getId(),
            savedUser.getEmail(),
            savedUser.getUsername(),
            savedUser.getPhone(),
            savedUser.getUserType()
        );
    }

    /**
     * Finds a user by their email address.
     *
     * @param email Email address to search for.
     * @return A UserResponseDTO with the user's details.
     * @throws RuntimeException if the user is not found.
     */
    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPhone(),
            user.getUserType()
        );
    }
}

