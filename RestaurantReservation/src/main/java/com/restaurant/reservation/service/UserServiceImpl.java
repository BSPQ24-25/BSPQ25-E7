package com.restaurant.reservation.service;

import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.dto.UserResponseDTO;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO registerUser(RegisterRequestDTO dto) {
        // Validar si ya existe el email
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        // Crear nuevo usuario
        User user = new User(
                dto.getEmail(),
                dto.getUsername(),
                dto.getPhone(),
                passwordEncoder.encode(dto.getPassword()), // ¡Contraseña cifrada!
                dto.getUserType()
        );

        User savedUser = userRepository.save(user);

        // Devolver DTO de respuesta
        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getUsername(),
                savedUser.getPhone(),
                savedUser.getUserType()
        );
    }
}
