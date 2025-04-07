package com.restaurant.reservation.service;

import com.restaurant.reservation.dto.LoginRequestDTO;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String authenticate(LoginRequestDTO loginRequestDTO) {
        // Buscar el usuario por el email
        Optional<User> userOptional = userRepository.findByEmail(loginRequestDTO.getEmail());

        // Verificar si el usuario existe
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }

        // Obtener el usuario
        User user = userOptional.get();

        // Verificar si la contraseña es correcta (usando BCrypt)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }

        // Si la autenticación es exitosa, generar el token JWT
        return jwtTokenProvider.generateToken(user);
    }
}
