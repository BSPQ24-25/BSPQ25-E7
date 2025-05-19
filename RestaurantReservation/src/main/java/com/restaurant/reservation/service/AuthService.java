package com.restaurant.reservation.service;

import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

/**
 * @class AuthService
 * @brief Servicio responsable del registro de nuevos usuarios.
 */
@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @brief Registra un nuevo usuario si no existe previamente.
     * @param dto Objeto que contiene los datos del usuario a registrar
     * @throws IllegalArgumentException si el usuario ya existe
     */
    public void register(RegisterRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("The user already exists");
        }

        User user = new User(
                dto.getEmail(),
                dto.getUsername(),
                dto.getPhone(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getUserType()
        );
        userRepository.save(user);
    }
}

