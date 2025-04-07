package com.restaurant.reservation.service;

import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(RegisterRequestDTO dto);
    UserResponseDTO getUserByEmail(String email);
}

