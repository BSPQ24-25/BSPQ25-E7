package com.restaurant.reservation.service;

import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.dto.UserResponseDTO;

/**
 * Interface for user-related business logic services.
 */
public interface UserService {

    /**
     * Registers a new user based on the provided data.
     *
     * @param dto DTO containing user registration details.
     * @return A UserResponseDTO with the saved user information.
     */
    UserResponseDTO registerUser(RegisterRequestDTO dto);

    /**
     * Retrieves a user by email.
     *
     * @param email Email of the user to find.
     * @return A UserResponseDTO with the user's data.
     */
    UserResponseDTO getUserByEmail(String email);
}

